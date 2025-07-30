package com.demo.ssrspringthyme.controller;

import com.demo.ssrspringthyme.model.Product;
import com.demo.ssrspringthyme.service.ProductService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    private ProductService service;

    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", service.findAll());
        model.addAttribute("pageTitle", "Products");
        model.addAttribute("metaDescription", "Browse a list of products with prices and details");
        return "products/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("pageTitle", "Create a Product");
        model.addAttribute("metaDescription", "Create a new product");
        model.addAttribute("metaRobots", "noindex,follow");
        return "products/new";
    }

    @PostMapping("/new")
    public String createProduct(@Valid @ModelAttribute Product product,
        BindingResult result,
        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Please correct the form errors");
            return "products/new";
        }

        service.save(product);
        redirectAttributes.addFlashAttribute("successMessage", "Product created successfully");
        return "redirect:/products";
    }

    @GetMapping("/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        Product product = service.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Product with ID " + id + " not found"));
        model.addAttribute("product", product);

        // SEO settings
        model.addAttribute("pageTitle", product.getName());
        model.addAttribute("metaDescription", "View details for product: " + product.getName());

        // canonical left as current URL (for detail pages)
        // Open Graph & Twitter settings
        model.addAttribute("ogTitle", product.getName());
        model.addAttribute("ogDescription", "Price: " + product.getPrice());
        // once images added, set a product-specific image as ogImage/twitterImage

        // JSON-LD data fields used by template
        model.addAttribute("jsonLdName", product.getName());
        model.addAttribute("jsonLdPrice", product.getPrice());
        model.addAttribute("jsonLdCurrency", "USD");

        return "products/view";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Product product = service.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Cannot edit: Product with ID " + id));
        model.addAttribute("product", product);
        model.addAttribute("pageTitle", "Edit: " + product.getName());
        model.addAttribute("metaDescription", "Edit product: " + product.getName());
        model.addAttribute("metaRobots", "noindex,follow");
        
        return "products/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateProduct(@PathVariable Long id, 
        @Valid @ModelAttribute("product") Product updatedProduct,
        BindingResult result,
        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("warningMessage", "There were validation issues");
            return "products/edit";
        }
        
        Product product = service.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Cannot update: Product with ID " + id));
        
        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        service.save(product);
        redirectAttributes.addFlashAttribute("successMessage", "Product updated successfully");
        return "redirect:/products";
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id,
        RedirectAttributes redirectAttributes) {
        Product product = service.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Cannot delete: Product with ID " + id));
        
        service.deleteById(product.getId());
        redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully");
        return "redirect:/products";
    }
}
