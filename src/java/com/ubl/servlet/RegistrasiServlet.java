package com.ubl.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author edwin < edwinkun at gmail dot com >
 */
@WebServlet(name = "RegistrasiServlet", urlPatterns = {"/RegistrasiServlet"})
public class RegistrasiServlet extends HttpServlet {

    // arraylist itu fungsinya seperti array, namun sudah diupgrade
    // disini berfungsi untuk menyimpan kumpulan object Siswa
    private List<Siswa> siswas = new ArrayList<>();

    /**
     * method GET untuk mengambil isi table
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        
        PrintWriter out = response.getWriter();
        
        // berikan json response kedepan, dalam format array
        // stringbuilder adalah class mirip String, namun string itu immutable, sedangkan stringbuilder tidak
        StringBuilder json = new StringBuilder("");
        
        // open json array
        json.append("[");
        for (Siswa siswa : siswas) {
            json.append("{\"nama\":\""+siswa.getNama()+"\", \"email\":\""+siswa.getEmail()+"\", \"nomerTelepon\":\""+siswa.getNomerTelepon()+"\"},");
        }
        
        // remove koma terakhir, jika json ada isinya
        if(json.length() > 0) 
            json.delete(json.length()-1, json.length());
        
        // close json array
        json.append("]");
        
        // send 
        out.println(json);
        
        // closing
        out.flush();
        out.close();
    }

    /**
     * method POST untuk mengisi data
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        
        PrintWriter out = response.getWriter();

        // ambil parameter dari depan
        String nama = request.getParameter("nama");
        String email = request.getParameter("email");
        String nomertelepon = request.getParameter("nomertelepon");
        
        // tambahkan ke arraylist
        Siswa siswa = new Siswa(nama, email, nomertelepon);
        siswas.add(siswa);
        
        // response
        out.println("{\"status\":1, \"keterangan\":\"Sukses\"}");
        
        // closing
        out.flush();
        out.close();
    }
    
    /**
     * private bean class, khusus untuk hold nilai saja
     */
    private class Siswa {
        
        // property yang di private, jika ingin diambil value-nya, ambil lewat method get
        private String nama;
        private String email;
        private String nomerTelepon;

        // constructor untuk mengisi nilai
        public Siswa(String nama, String email, String nomerTelepon) {
            this.nama = nama;
            this.email = email;
            this.nomerTelepon = nomerTelepon;
        }

        // default constructor
        public Siswa() {
        }

        // setter and getter untuk hold nilai        
        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getNomerTelepon() {
            return nomerTelepon;
        }

        public void setNomerTelepon(String nomerTelepon) {
            this.nomerTelepon = nomerTelepon;
        }
    }
}
