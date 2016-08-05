package com.kolyadko.likeit.controller;

import com.kolyadko.likeit.command.Command;
import com.kolyadko.likeit.command.factory.ActionCommandFactory;
import com.kolyadko.likeit.command.factory.ShowCommandFactory;
import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.util.MappingManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by DaryaKolyadko on 25.07.2016.
 */
@WebServlet(name = "LikeItServlet")
public class LikeItServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestContent requestContent = new RequestContent(request);
        Command command = ActionCommandFactory.getCommand(requestContent);
        String page = command.execute(requestContent);
        requestContent.insertValues(request);

        if (page == null) {
            page = MappingManager.HOME_PAGE;
        }

        response.sendRedirect(page);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestContent requestContent = new RequestContent(request);
        Command command = ShowCommandFactory.getCommand(requestContent);
        String page = command.execute(requestContent);

        if (page == null) {
            command = ShowCommandFactory.getCommand(MappingManager.HOME_PAGE);
            page = command.execute(requestContent);
        }

        requestContent.insertValues(request);
        getServletContext().getRequestDispatcher(page).forward(request, response);
    }
}