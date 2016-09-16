package com.kolyadko.likeit.controller;

import com.kolyadko.likeit.command.Command;
import com.kolyadko.likeit.command.factory.ActionCommandFactory;
import com.kolyadko.likeit.command.factory.ShowCommandFactory;
import com.kolyadko.likeit.content.RequestContent;
import com.kolyadko.likeit.exception.CommandException;
import com.kolyadko.likeit.memorycontainer.impl.ObjectMemoryContainer;
import com.kolyadko.likeit.pool.ConnectionPool;
import com.kolyadko.likeit.type.MemoryContainerType;
import com.kolyadko.likeit.util.MappingManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by DaryaKolyadko on 25.07.2016.
 */

/**
 * Servlet
 */
@WebServlet(name = "LikeItServlet")
public class LikeItServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(LikeItServlet.class);
    private static final String EXCEPTION = "exceptionContainer";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestContent requestContent = new RequestContent(request);
        Command command = ActionCommandFactory.getCommand(requestContent);
        String page;

        try {
            page = command.execute(requestContent);
        } catch (CommandException e) {
            LOG.error(e);
            requestContent.setSessionAttribute(EXCEPTION, new ObjectMemoryContainer(e, MemoryContainerType.ONE_OFF));
            page = MappingManager.ERROR_PAGE_500;
        }

        requestContent.insertValues(request);
        response.sendRedirect(page);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestContent requestContent = new RequestContent(request);
        Command command = ShowCommandFactory.getCommand(requestContent);
        String page;

        try {
            page = command.execute(requestContent);
        } catch (CommandException e) {
            LOG.error(e);
            requestContent.setSessionAttribute(EXCEPTION, new ObjectMemoryContainer(e, MemoryContainerType.ONE_OFF));
            page = MappingManager.getInstance().getProperty(MappingManager.ERROR_PAGE_500);
        }

        requestContent.insertValues(request);
        getServletContext().getRequestDispatcher(page).forward(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();

        if (ConnectionPool.isInitialized()) {
            ConnectionPool.getInstance().closeAll();
        }
    }
}