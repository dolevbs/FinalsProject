<%@page import="java.awt.image.BufferedImage"%>
<%@page contentType="application/json" pageEncoding="UTF-8" session="true" import="java.util.*, logic.Manager"%>
<%
    Manager manager = new Manager();
    if ("getProduct".equals(request.getParameter("action"))) {
        out.print(manager.getProductName((String) request.getParameter("barcode")));
    } else if ("getUserList".equals(request.getParameter("action"))) {
        out.print(manager.getUserProductList((String) request.getParameter("id")));
    } else if ("addUserProduct".equals(request.getParameter("action"))) {
        String id = (String) request.getParameter("id");
        String barcode = (String) request.getParameter("barcode");
        String img =  (String) request.getParameter("image");
        out.print(manager.AddUserProduct(id, barcode, img));
    }
        
%>