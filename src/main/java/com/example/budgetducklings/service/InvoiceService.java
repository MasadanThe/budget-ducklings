package com.example.budgetducklings.service;

import com.example.budgetducklings.model.Payment;
import com.example.budgetducklings.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class InvoiceService {
    private InvoiceRepository invoiceRepository;
    private Payment editPayment;

    public InvoiceService(){
        invoiceRepository = new InvoiceRepository();
    }

    //Redirect to invoiceRepository to create a payment
    public void createPayment(String username,String title,String date,String description,String category,String price){
        double priceAsDouble;
        try {
            priceAsDouble = Double.parseDouble(price);
        }
        catch (Exception e)
        {
            priceAsDouble = 0;
        }
        Payment payment = new Payment(username, title, date, description, category,priceAsDouble);
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
    public void updatePayment(String title,String date,String description,String category,String price){
        double priceAsDouble;
        Payment payment = editPayment;
        if(!title.equals(""))
        {
            payment.setTitle(title);
        }
        if(!date.equals(""))
        {
            payment.setDate(date);
        }
        if(!description.equals(""))
        {
            payment.setDescription(description);
        }
        if(!category.equals(""))
        {
            payment.setCategory(category);
        }
            if (!price.equals("")) {
                //Try for handling none doubles
                try {
                    priceAsDouble = Double.parseDouble(price);
                }
                catch (Exception e)
                {
                    priceAsDouble = 0;
                }
                payment.setPrice(priceAsDouble);
            }
        invoiceRepository.updatePayment(payment);
    }

    //Deletes a payment based on username and id
    public void deletePayment(){
        invoiceRepository.deletePayment(editPayment.getUsername(), editPayment.getId());
    }
}
