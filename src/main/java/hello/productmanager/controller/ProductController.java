package hello.productmanager.controller;

import hello.productmanager.domain.DeleteProduct;
import hello.productmanager.domain.Product;
import hello.productmanager.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/views/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping
    private String products(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);

        return "views/products";
    }

    @GetMapping("/{productId}")
    private String product(@PathVariable long productId, Model model) {
        productRepository.findById(productId);
        Product product = productRepository.findById(productId);
        model.addAttribute("product", product);

        return "views/product";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("product", new Product());

        return "views/add";
    }

    @PostMapping("/add")
    public String save(@Validated @ModelAttribute Product product, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult);

            return "views/add";
        }

        Product savedProduct = productRepository.save(product);
        redirectAttributes.addAttribute("productId", savedProduct.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/views/products/{productId}";
    }

    @GetMapping("/delete")
    public String deleteView(Model model) {
        model.addAttribute("product", new DeleteProduct());

        return "views/delete";
    }

    @PostMapping("/delete")
    public String delete(@Validated @ModelAttribute("product") DeleteProduct delete,
                         BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult);

            return "views/delete";
        }

        if (productRepository.findById(delete.getId()) == null) {
            model.addAttribute("isExist", false);

            return "views/delete";
        }

        productRepository.delete(delete.getId());

        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);

        return "views/products";
    }

    @GetMapping("/{productId}/edit")
    public String editForm(@PathVariable Long productId, Model model) {
        Product product = productRepository.findById(productId);
        model.addAttribute("product", product);

        return "views/edit";
    }

    @PostMapping("/{productId}/edit")
    public String edit(@PathVariable Long productId, @Validated @ModelAttribute Product product,
                       BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult);

            return "views/edit";
        }

        productRepository.update(productId, product);
        redirectAttributes.addAttribute("productId", productId);
        redirectAttributes.addAttribute("status", true);

        return "redirect:/views/products/{productId}";
    }

}