package api.controllers;

import api.models.Post;
import api.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private PostService postService;

    @GetMapping("/blog")
    public String blog(Model model) {
        model.addAttribute("title", "Blog");
        Iterable<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "blog";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogAddPost(@RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
        Post post = new Post(title, anons, full_text);
        postService.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model) {
        if (!postService.existById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> post = postService.findById(id);
        ArrayList<Post> list = new ArrayList<>();
        post.ifPresent(list::add);
        model.addAttribute("post", list);
        model.addAttribute("title", "Details");
        return "blog-details";
    }

    @PostMapping("/blog/{id}/like")
    public String blogDetailsAddView(@PathVariable(value = "id") long id, Model model) {
        if (!postService.existById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> post = postService.findById(id);
        post.ifPresent(post1 -> postService.addView(post1));
        post.ifPresent(post1 -> postService.save(post1));
        return "redirect:/blog/{id}";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogPostEdit(@PathVariable(value = "id") long id, Model model) {
        if (!postService.existById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> post = postService.findById(id);
        ArrayList<Post> list = new ArrayList<>();
        post.ifPresent(list::add);
        model.addAttribute("post", list);
        model.addAttribute("title", "Details");
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full_text, Model model) {
        Post post = postService.findById(id).orElseThrow(() -> new NullPointerException("Blog with id " + id + " doesn't exist"));
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postService.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") long id, Model model) {
        Post post = postService.findById(id).orElseThrow();
        postService.remove(post);
        return "redirect:/blog";
    }

}
