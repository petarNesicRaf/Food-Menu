package com.example.chooseyourfood;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.servlet.annotation.WebServlet;
import java.io.PrintWriter;

@WebServlet(name = "submitServlet", value = "/submit")
public class SubmitServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        CopyOnWriteArrayList<String> korisnici = (CopyOnWriteArrayList<String>) getServletContext().getAttribute(HelloServlet.KORISNICI);
        ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> menu = (ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>>) getServletContext().getAttribute(HelloServlet.PORUDZBINA);
        korisnici.add(req.getSession().getId());

        menu.get("ponedeljak").putIfAbsent(req.getParameter("ponedeljak"), 0);
        menu.get("ponedeljak").put(req.getParameter("ponedeljak"), menu.get("ponedeljak").get(req.getParameter("ponedeljak")) + 1);

        menu.get("utorak").putIfAbsent(req.getParameter("utorak"), 0);
        menu.get("utorak").put(req.getParameter("utorak"), menu.get("utorak").get(req.getParameter("utorak")) + 1);

        menu.get("sreda").putIfAbsent(req.getParameter("sreda"), 0);
        menu.get("sreda").put(req.getParameter("sreda"), menu.get("sreda").get(req.getParameter("sreda")) + 1);

        menu.get("cetvrtak").putIfAbsent(req.getParameter("cetvrtak"), 0);
        menu.get("cetvrtak").put(req.getParameter("cetvrtak"), menu.get("cetvrtak").get(req.getParameter("cetvrtak")) + 1);

        menu.get("petak").putIfAbsent(req.getParameter("petak"), 0);
        menu.get("petak").put(req.getParameter("petak"), menu.get("petak").get(req.getParameter("petak")) + 1);


        System.out.println("order: " + menu);

        req.getSession().setAttribute("ponedeljak", req.getParameter("ponedeljak"));
        req.getSession().setAttribute("utorak", req.getParameter("utorak"));
        req.getSession().setAttribute("sreda", req.getParameter("sreda"));
        req.getSession().setAttribute("cetvrtak", req.getParameter("cetvrtak"));
        req.getSession().setAttribute("petak", req.getParameter("petak"));

        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h1>Vasa porudzbina je potvrdjena!</h1>");
        out.println("<ul>");
        out.println("<li>Ponedeljak - " + req.getParameter("ponedeljak") + "</li>");
        out.println("<li>Utorak - " + req.getParameter("utorak") + "</li>");
        out.println("<li>Sreda - " + req.getParameter("sreda") + "</li>");
        out.println("<li>Cetvrtak - " + req.getParameter("cetvrtak") + "</li>");
        out.println("<li>Petak - " + req.getParameter("petak") + "</li>");
        out.println("</ul>");

        out.println("<form method=\"get\" action=\"/odabrana-jela\">");
        out.println("<input type=\"hidden\" name=\"password\" value=\"1234\">");
        out.println("<button type=\"submit\">Odabrana jela</button>");
        out.println("</form>");

        out.println("</body></html>");

    }
}
