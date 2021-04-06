package com.example.demo;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CallController implements ErrorController {

	@Autowired
	ExpenseRepository repository;
	
	@SuppressWarnings("unused")
	@RequestMapping(value = "/run")
	public Expense StringexceptionHanlder() {
		System.out.println("run method call");
		Expense expense = new Expense();
		if (expense == null)
		{
			throw new ExpenseNotFoundException();
		}
		throw new ExpenseNotFoundException();
	}
	
	@RequestMapping(value = "/home")
	public String run() {
		System.out.println("home method called");
		return "jsp/home.jsp";
	}

	@RequestMapping(value = "/home1")
	public String run1() {
		System.out.println("home method called");
		repository.save(new Expense("breakfast", 5));
		repository.save(new Expense("coffee", 2));
		repository.save(new Expense("New SSD drive", 200));
		repository.save(new Expense("Tution for baby", 350));
		repository.save(new Expense("Some apples", 5));

		Iterable<Expense> iterator = repository.findAll();

		System.out.println("All expense items: ");
		iterator.forEach(item -> System.out.println(item));

		List<Expense> breakfast = repository.findByItem("breakfast");
		System.out.println("\nHow does my breakfast cost?: ");
		breakfast.forEach(item -> System.out.println(item));

		List<Expense> expensiveItems = repository.listItemsWithPriceOver(200);
		System.out.println("\nExpensive Items: ");
		expensiveItems.forEach(item -> System.out.println(item));
		return "jsp1/home.jsp";
	}

	@Override
	public String getErrorPath() {
		 return "/error";
	}

	@RequestMapping(value = "/error")
    public String handleError(HttpServletRequest request) {
        String errorPage = "error"; // default
         
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
         
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
             
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                // handle HTTP 404 Not Found error
                errorPage = "error/404";
                 
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                // handle HTTP 403 Forbidden error
                errorPage = "error/403";
                 
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                // handle HTTP 500 Internal Server error
                errorPage = "error/500";
                 
            }
        }
         
        return errorPage;
    }
	
}
