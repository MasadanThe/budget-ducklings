package com.example.budgetducklings.service;

import com.example.budgetducklings.model.Payment;
import com.example.budgetducklings.repository.InvoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;

@Service
public class InvoiceService {
    private InvoiceRepository invoiceRepository;
    private Payment editPayment;

    public InvoiceService(){
        invoiceRepository = new InvoiceRepository();
    }

    //Redirect to invoiceRepository to create a payment
    public void createPayment(String username, Payment payment){
        payment.setUsername(username);
        invoiceRepository.createPayment(payment);
    }

    public ArrayList<Payment> getPayments(String username){
        return invoiceRepository.getPayments(username);
    }

    //Gets a payment and adds it to editPayment to save for later use
    public Payment getOnePayment(String username, int id){
        Payment payment = invoiceRepository.getOnePayment(username,id);
        if(payment == null)
        {
            return null;
        }
        editPayment = payment;

        return payment;
    }

    //Updates the Payment in editPayment and sends the data to invoiceRepository
    public void updatePayment(Payment payment){
        double priceAsDouble;
        Payment payment1 = editPayment;
        if(!payment.getTitle().equals(""))
        {
            payment1.setTitle(payment.getTitle());
        }
        if(!payment.getDate().equals(""))
        {
            payment1.setDate(payment.getDate());
        }
        if(!payment.getDescription().equals(""))
        {
            payment1.setDescription(payment.getDescription());
        }
        if(!payment.getCategory().equals(""))
        {
            payment1.setCategory(payment.getCategory());
        }
            if (!payment.getPrice().equals("")) {
                payment1.setPrice(payment.getPrice());
            }
        invoiceRepository.updatePayment(payment);
    }

    //Deletes a payment based on username and id
    public void deletePayment(){
        invoiceRepository.deletePayment(editPayment.getUsername(), editPayment.getId());
    }
}
