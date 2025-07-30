package com.demo.ssrspringthyme.controller;

import com.demo.ssrspringthyme.model.Product;
import com.demo.ssrspringthyme.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class SeoController {
    
    @Autowired
    ProductService service;

    @GetMapping(value = "/sitemap.xml", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public String sitemap(HttpServletRequest request) {
        // build site root
        String siteRoot = UriComponentsBuilder.fromHttpUrl(getSiteBaseUrl(request)).build().toUriString();
        if (siteRoot.endsWith("/")) {
            siteRoot = siteRoot.substring(0, siteRoot.length() - 1);
        }

        // static pages
        StringBuilder xml = new StringBuilder();
        xml.append("""
            <?xml version="1.0" encoding="UTF-8"?>
            <urlset xmlns:"http://www.sitemaps.org/schemas/sitemap/0.9">            
        """);

        // current date as fallback
        String today = LocalDate.now().format(DateTimeFormatter.ISO_DATE);

        // pages
        xml.append(urlEntry(siteRoot + "/", today));
        xml.append(urlEntry(siteRoot + "/about", today));
        xml.append(urlEntry(siteRoot + "/products", today));

        // dynamic product view pages
        List<Product> products = service.findAll();
        for (Product p : products) {
            xml.append(urlEntry(siteRoot + "/products/" + p.getId(), today));
        }

        xml.append("</urlset>");
        return xml.toString();
    }

    private static String urlEntry(String loc, String lastmod) {
        return """
            <url>
                <loc>%s</loc>
                <lastmod>%s</lastmod>
                <changefreq>weekly</changefreq>
                <priority>0.8</priority>
            </url>
        """.formatted(xmlEscape(loc), xmlEscape(lastmod));
    }

    private static String xmlEscape(String s) {
        return s.replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&apos;");
    }

    private static String getSiteBaseUrl(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String uri = request.getRequestURI();

        return url.substring(0, url.length() - uri.length());
    }
}
