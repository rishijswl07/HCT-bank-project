package com.Bank.project.service;


import com.Bank.project.Exception.InvalidException;
import com.Bank.project.controller.GenerateRandom;
import com.Bank.project.entites.*;
import com.Bank.project.reposistry.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    Acc_BalanceRepo accBalanceRepo;
    @Autowired
    Cust_Acc_MapRepo custMapRepo;
    @Autowired
    TransactionRepo accTransRepo;
    @Autowired
    cust_AddressRepo addressRepo;
    @Autowired
    CustomerRepo customerRepo;

    public CustomerDetailedAddress addCustomer(CustomerDetailedAddress custDetAddress) {
        GenerateRandom ran = new GenerateRandom();
        cust_Details del = new cust_Details();
        long cid = ran.getRandom();
        del.setCust_Id(cid);
        del.setName(custDetAddress.getName());
        del.setPhone(custDetAddress.getPhone());
        del.setEmail(custDetAddress.getEmail());
        Timestamp td = new Timestamp(new Date().getTime());
        del.setCreated(td);
        del.setLastUpadated(td);

        cust_Address addr = new cust_Address();
        long aid = ran.getRandom();
        addr.setAddress_Id(aid);
        addr.setCity(custDetAddress.getCity());
        addr.setPIN(custDetAddress.getPin());
        addr.setCountry(custDetAddress.getCountry());
        addr.setAddressLane(custDetAddress.getAddresslane());
        addr.setLastUpdate(td);

        Acc_Balance ab = new Acc_Balance();
        long acc_Id = ran.getRandom();
        ab.setAcc_Id(acc_Id);
        ab.setBalance(500.00);

        cust_Address savedAddress = addressRepo.save(addr);
        del.setCustAddress(savedAddress);

        cust_Details savedCustDetails = customerRepo.save(del);
        Acc_Balance accsaved = accBalanceRepo.save(ab);

        cust_Acc_Map custMap = new cust_Acc_Map();
        custMap.setCustDetails(savedCustDetails);
        custMap.setAccbalance(accsaved);
        custMapRepo.save(custMap);
        return custDetAddress;
    }
    public List<cust_Details> getCustDetails(){
        List<cust_Details> details1 = customerRepo.findAll();
        List<cust_Details> custdm = new ArrayList<>();
        for(cust_Details customerD:details1){
            cust_Details custdet = new cust_Details();
            custdet.setCust_Id(customerD.getCust_Id());
            custdet.setName(customerD.getName());
            custdet.setEmail(customerD.getEmail());
            custdet.setPhone(customerD.getPhone());
            custdet.setCreated(customerD.getCreated());
            custdet.setLastUpadated(customerD.getLastUpadated());
            custdm.add(custdet);
        }
        return  custdm;
    }
    public List<cust_Address> getCustAddress(){
        List<cust_Address> addr1 = addressRepo.findAll();
        List<cust_Address> addre= new ArrayList<>();
        for(cust_Address custadd : addr1){
            cust_Address addr = new cust_Address();
            addr.setAddress_Id(custadd.getAddress_Id());
            addr.setPIN(custadd.getPIN());
            addr.setAddressLane(custadd.getAddressLane());
            addr.setCity(custadd.getCity());
            addr.setCountry(custadd.getCountry());
            addr.setLastUpdate(custadd.getLastUpdate());
            addre.add(addr);
        }
        return addre;
    }
    public CustomerDetailedAddress updateCustomer(Long cust_Id, CustomerDetailedAddress custDetAddress) {

        cust_Details custDetails = customerRepo.findById(cust_Id).get();

        custDetails.setName(custDetAddress.getName());
        custDetails.setPhone(custDetAddress.getPhone());
        custDetails.setEmail(custDetAddress.getEmail());


        cust_Address custAddress = custDetails.getCustAddress();


        custAddress.setCountry(custDetAddress.getCountry());
        custAddress.setCity(custDetAddress.getCity());
        custAddress.setAddressLane(custDetAddress.getAddresslane());
        custAddress.setPIN(custDetAddress.getPin());

        customerRepo.save(custDetails);
        addressRepo.save(custAddress);
        return custDetAddress;
    }
    public Acc_Balance getBalance(long acc_Id){
        Acc_Balance ret = accBalanceRepo.findById(acc_Id).get();
        return ret;
    }
    public Acc_Balance findByCustId(Long cust_Id) {
        cust_Details custDetails = customerRepo.findById(cust_Id).get();
        cust_Acc_Map custMap = custDetails.getCustMap();
        Acc_Balance accBalance = custMap.getAccbalance();
        return accBalance;
    }
    public ResponseEntity<String> createTransaction(NewTransaction trans) throws InvalidException {
        GenerateRandom ran = new GenerateRandom();
        long rid = ran.getRandom();
        long acc_id = trans.getAcc_id();
        long to_acc_id = trans.getTo_acc_id();
        String type = trans.getType();
        double amount = trans.getAmount();
        Timestamp td = new Timestamp(new Date().getTime());
        Acc_Transactions accTransactions1 = new Acc_Transactions();
        Acc_Transactions accTransaction2 = new Acc_Transactions();
        accTransactions1.setTransactionRefId(rid);
        accTransactions1.setCredit(0.0);
        accTransactions1.setDebit(amount);
        accTransactions1.setLastUpdated(td);
        accTransactions1.setAcc_Id(acc_id);

        accTransaction2.setTransactionRefId(rid);
        accTransaction2.setCredit(amount);
        accTransaction2.setDebit(0.0);
        accTransaction2.setLastUpdated(td);
        accTransaction2.setAcc_Id(to_acc_id);


            Acc_Balance sender = accBalanceRepo.findById(acc_id).get();
            double accountbal = sender.getBalance();
            if (accountbal >= amount) {
                sender.setBalance(accountbal - amount);
                accTransactions1.setAvlBalance(sender.getBalance());
                Acc_Balance abs = accBalanceRepo.save(sender);
                accTransactions1.setAccBalance(abs);
            } else {
                try {
                    throw new InvalidException("Balance cannot be debited", "HCT400");
                }
                catch(InvalidException e)
                {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()+"\n"+e.getReasoncode());
                }
            }

        Acc_Balance reciever = accBalanceRepo.findById(to_acc_id).get();
        double real = reciever.getBalance();
        reciever.setBalance(real+amount);
        accTransaction2.setAvlBalance(reciever.getBalance());
        Acc_Balance ab = accBalanceRepo.save(reciever);
        accTransaction2.setAccBalance(ab);

        accTransRepo.save(accTransactions1);
        accTransRepo.save(accTransaction2);

        return ResponseEntity.ok("Transaction Successfully");
    }
    public List<Acc_Transactions> getByAccId(Acc_Balance accBalance){
        List<Acc_Transactions> acctrans = new ArrayList<>();
        List<Acc_Transactions> list = accTransRepo.findByaccBalance(accBalance);
        for(Acc_Transactions abd : list) {
            Acc_Transactions ad = new Acc_Transactions();
            ad.setTransactionId(abd.getTransactionId());
            ad.setTransactionRefId(abd.getTransactionRefId());
            ad.setCredit(abd.getCredit());
            ad.setDebit(abd.getDebit());
            ad.setLastUpdated(abd.getLastUpdated());
            ad.setAvlBalance(abd.getAvlBalance());
            acctrans.add(ad);
        }
        return acctrans;
    }
    public List<Acc_Transactions> getByRefId(Acc_Transactions transactionRefId){
        List<Acc_Transactions> transactions = new ArrayList<>();
        List<Acc_Transactions> transac = accTransRepo.findBytransactionRefId(transactionRefId);
        for(Acc_Transactions a : transac){
            Acc_Transactions accounttrans = new Acc_Transactions();
            accounttrans.setTransactionId(a.getTransactionId());
            accounttrans.setTransactionRefId(a.getTransactionRefId());
            accounttrans.setCredit(a.getCredit());
            accounttrans.setDebit(a.getDebit());
            accounttrans.setAvlBalance(a.getAvlBalance());
            transactions.add(accounttrans);
        }
        return transactions;
    }
    public Acc_Transactions getByRefIdAccId(Long transRefId, Acc_Balance accBalance){
        return accTransRepo.findBytransactionRefIdAndAccBalance(transRefId,accBalance).orElse(null);
    }
}
