package com.example.budgetducklings.controller;

import com.example.budgetducklings.model.Payment;
import com.example.budgetducklings.service.InvoiceService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
@RequestMapping
public class InvoiceController {
    private InvoiceService invoiceService;

    public InvoiceController(){
        invoiceService = new InvoiceService();
    }

    //Shows all of a users payments
    @GetMapping("invoice")
    public String getInvoicePage(Model model, HttpSession session){
        if(session.getAttribute("username") != null)
        {
            String username =(String) session.getAttribute("username");

            ArrayList<Payment> payments = invoiceService.getPayments(username);

            model.addAttribute("payments", payments);
            return "invoicePage";
        }
        return "redirect:login";
    }

    @GetMapping("payment")
    public String showPaymentPage(HttpSession session){
        if (session.getAttribute("username") != null){
            return "redirect:paymentPage.html";
        }
        return "redirect:login";
    }
    //Creates a payment
    @PostMapping("payment")
    public String createPayment(HttpSession session, @RequestParam String title, @RequestParam String date, @RequestParam String description, @RequestParam String category, @RequestParam String price)
    {
        if (session.getAttribute("username") != null) {
            String username = (String) session.getAttribute("username");
            invoiceService.createPayment(username, title, date, description, category, price);

            return "redirect:invoice";
        }
        return "redirect:login";
    }

    //Gets the information on the Payment that will be edited/deleted and writes it to editPage.html
    @GetMapping("edit")
    public String showEditPage(Model model, HttpSession session, @RequestParam int id){
        if (session.getAttribute("username") != null){
            String username =(String) session.getAttribute("username");

            ArrayList<Payment> payment = new ArrayList<>();
            Payment foundPayment = invoiceService.getOnePayment(username, id);
            if (foundPayment == null)
            {
                return "redirect:invoice";
            }
            payment.add(foundPayment);

            model.addAttribute("payment", payment);

            return "editPage.html";
        }
        return "redirect:login";
    }

    //Gets the input data and sends it to invoiceService for update
    @PostMapping("edit")
    public String showEditId(HttpSession session, @RequestParam String title, @RequestParam String date, @RequestParam String description, @RequestParam String category, @RequestParam String price){
        if (session.getAttribute("username") != null) {
            invoiceService.updatePayment(title, date, description, category, price);
            return "redirect:invoice";
        }

        return"redirect:login";
    }

    //Send delete command to invoiceService
    @PostMapping("delete")
    public String delete(HttpSession session){
        if (session.getAttribute("username") != null) {
            invoiceService.deletePayment();
            return "redirect:invoice";
        }

        return"redirect:login";

    }


}
