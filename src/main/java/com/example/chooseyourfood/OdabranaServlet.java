package com.example.chooseyourfood;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.System.out;


@WebServlet(name = "OdabranaJela", value = "/odabrana-jela")
public class OdabranaServlet extends HttpServlet {
    private String password = "";

    public void init()
    {
        try {
            Scanner sc = new Scanner(new File("C:\\Users\\T450s\\Desktop\\Choose your food\\src\\main\\resources\\password\\password.txt"));
            password = sc.nextLine();
            out.println(password);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();

        ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> meni = (ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>>) getServletContext().getAttribute(HelloServlet.PORUDZBINA);
        out.println("<html>");

        /*
        String cssTag="<link rel=\"stylesheet\" type=\"text/css\" href=\"webapp/css/style.css\">";
        */
        out.println("<body>");

        if(req.getParameter("password") != null && req.getParameter("password").equals(password))
        {
            out.println("<h1>Odabrana jela</h1>");



            out.println("<form method=\"post\" action=\"/odabrana-jela?password=" + password + "\">");
            out.println("<button type=\"submit\">Ocisti</button>");
            out.println("</form>");

            out.println("<h1>PONEDELJAK</h1>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Jelo</th>");
            out.println("<th>Kolicina</th>");
            out.println("</tr>");

            for (Map.Entry entry : meni.get("ponedeljak").entrySet())
            {
                out.println("<tr>");
                out.println("<td>" + entry.getKey() + "</td>");
                out.println("<td>" + entry.getValue() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");

            out.println("<h1>UTORAK</h1>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Jelo</th>");
            out.println("<th>Kolicina</th>");
            out.println("</tr>");

            for (Map.Entry entry : meni.get("utorak").entrySet()) {
                out.println("<tr>");
                out.println("<td>" + entry.getKey() + "</td>");
                out.println("<td>" + entry.getValue() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");

            out.println("<h1>SREDA</h1>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Jelo</th>");
            out.println("<th>Kolicina</th>");
            out.println("</tr>");

            for (Map.Entry entry : meni.get("sreda").entrySet()) {
                out.println("<tr>");
                out.println("<td>" + entry.getKey() + "</td>");
                out.println("<td>" + entry.getValue() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");

            out.println("<h1>CETVRTAK</h1>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Jelo</th>");
            out.println("<th>Kolicina</th>");
            out.println("</tr>");
            for (Map.Entry entry : meni.get("cetvrtak").entrySet()) {
                out.println("<tr>");
                out.println("<td>" + entry.getKey() + "</td>");
                out.println("<td>" + entry.getValue() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");

            out.println("<h1>PETAK</h1>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Jelo</th>");
            out.println("<th>Kolicina</th>");
            out.println("</tr>");
            for (Map.Entry entry : meni.get("petak").entrySet()) {
                out.println("<tr>");
                out.println("<td>" + entry.getKey() + "</td>");
                out.println("<td>" + entry.getValue() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("<br></br>");
            out.println("<br></br>");


            out.println("<form method=\"get\" action=\"/" + "\">");
            out.println("<button type=\"submit\">Odabir jela</button>");
            out.println("</form>");
        }else{
            resp.setStatus(403);
            out.println("<h1>UNAUTHORIZED</h1>");
        }
        out.println("</html></body>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> meni = (ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>>) getServletContext().getAttribute(HelloServlet.PORUDZBINA);
        CopyOnWriteArrayList<String> korisnici = (CopyOnWriteArrayList<String>) getServletContext().getAttribute(HelloServlet.KORISNICI);

        meni.get("ponedeljak").clear();
        meni.get("utorak").clear();
        meni.get("sreda").clear();
        meni.get("cetvrtak").clear();
        meni.get("petak").clear();
        korisnici.clear();

    }
}
