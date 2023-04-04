package com.example.chooseyourfood;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/")
public class HelloServlet extends HttpServlet {
    private Map<String, List<String>> meni = new HashMap<>();
    private List<String> ponedeljak = new ArrayList<>();
    private List<String> utorak = new ArrayList<>();
    private List<String> sreda = new ArrayList<>();
    private List<String> cetvrtak = new ArrayList<>();
    private List<String> petak = new ArrayList<>();
    public static final String KORISNICI = "korisnici";
    public static final String PORUDZBINA = "porudzbina";


    public void init() {
        ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> porudzbina = new ConcurrentHashMap<>();
        porudzbina.put("ponedeljak", new ConcurrentHashMap<>());
        porudzbina.put("utorak",      new ConcurrentHashMap<>());
        porudzbina.put("sreda",       new ConcurrentHashMap<>());
        porudzbina.put("cetvrtak",    new ConcurrentHashMap<>());
        porudzbina.put("petak",       new ConcurrentHashMap<>());


        getServletContext().setAttribute(KORISNICI, new CopyOnWriteArrayList<>());
        getServletContext().setAttribute(PORUDZBINA, porudzbina);


        meni.put("ponedeljak",  this.ponedeljak);
        meni.put("utorak",      this.utorak);
        meni.put("sreda",       this.sreda);
        meni.put("cetvrtak",    this.cetvrtak);
        meni.put("petak",       this.petak);

        initMeni("ponedeljak");
        initMeni("utorak");
        initMeni("sreda");
        initMeni("cetvrtak");
        initMeni("petak");
    }

    private void initMeni(String dan)
    {
        File odabranDan = new File("C:\\Users\\T450s\\Desktop\\Choose your food\\src\\main\\resources\\dani\\"+ dan + ".txt");
        try {
            Scanner sc = new Scanner(odabranDan);
            while(sc.hasNextLine()){
                meni.get(dan).add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        CopyOnWriteArrayList<String> submittedUsers = (CopyOnWriteArrayList<String>) getServletContext().getAttribute(KORISNICI);
        if(!submittedUsers.contains(req.getSession().getId())){
            out.println("<h1>Odaberite vas rucak:</h1>");
            out.println("<form action=\"submit\" method=\"post\">");

            out.println("<label for=\"ponedeljak\">Ponedeljak:</label><br/>");
            out.println("<select id=\"ponedeljak\" name=\"ponedeljak\">");
            for (String food : this.meni.get("ponedeljak")) {
                out.println("<option value=\"" + food + "\">" + food + "</option>");
            }
            out.println("</select><br/>");


            out.println("<label for=\"utorak\">Utorak:</label><br/>");
            out.println("<select id=\"utorak\" name=\"utorak\">");
            for (String food : this.meni.get("utorak")) {
                out.println("<option value=\"" + food + "\">" + food + "</option>");
            }
            out.println("</select><br/>");

            out.println("<label for=\"sreda\">Sreda:</label><br/>");
            out.println("<select id=\"sreda\" name=\"sreda\">");
            for (String food : this.meni.get("sreda")) {
                out.println("<option value=\"" + food + "\">" + food + "</option>");
            }
            out.println("</select><br/>");

            out.println("<label for=\"cetvrtak\">Cetvrtak:</label><br/>");
            out.println("<select id=\"cetvrtak\" name=\"cetvrtak\">");
            for (String food : this.meni.get("cetvrtak")) {
                out.println("<option value=\"" + food + "\">" +food + "</option>");
            }
            out.println("</select><br/>");


            out.println("<label for=\"petak\">Petak:</label><br/>");
            out.println("<select id=\"petak\" name=\"petak\">");
            for (String food : this.meni.get("petak")) {
                out.println("<option value=\"" + food + "\">" + food + "</option>");
            }
            out.println("</select><br/>");

            out.println("<br/><button type=\"submit\">Potvrdite unos</button>");
            out.println("</form>");
        } else {
            out.println("<h1>Vec ste potvrdili vasu porudzbinu za ovu nedelju</h1>");
            out.println("<ul>");
            out.println("<li><strong>Ponedeljak</strong> - " + req.getSession().getAttribute("ponedeljak") + "</li>");
            out.println("<li><strong>Utorak</strong> - " + req.getSession().getAttribute("utorak") + "</li>");
            out.println("<li><strong>Sreda</strong> - " + req.getSession().getAttribute("sreda") + "</li>");
            out.println("<li><strong>Cetvrtak</strong> - " + req.getSession().getAttribute("cetvrtak") + "</li>");
            out.println("<li><strong>Petak</strong> - " + req.getSession().getAttribute("petak") + "</li>");
            out.println("</ul>");

            out.println("<form method=\"get\" action=\"/odabrana-jela\">");
            out.println("<input type=\"hidden\" name=\"password\" value=\"1234\">");
            out.println("<button type=\"submit\">Odabrana jela</button>");
            out.println("</form>");
        }
        out.println("</body></html>");
    }

    public void destroy() {
    }
}