package com.ratella.ReactiveReadingListWebApp.controller;

import com.ratella.ReactiveReadingListWebApp.model.Book;
import com.ratella.ReactiveReadingListWebApp.model.ReadingListRepository;
import com.ratella.ReactiveReadingListWebApp.model.Recommendation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class ReadingListController {
  private final WebClient webClient;
  private ReadingListRepository readingListRepository;
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  public ReadingListController(
      ReadingListRepository readingListRepository, WebClient.Builder webClientBuilder) {
    this.readingListRepository = readingListRepository;
    this.webClient = webClientBuilder.baseUrl("http://localhost:9090").build();
  }

  @GetMapping(value = "/readingList")
  public String readersBooks(Model model) {
    Flux<Book> readingList = readingListRepository.findByReader("Tella");
    IReactiveDataDriverContextVariable bookReactiveDataDrivenMode =
        new ReactiveDataDriverContextVariable(readingList, 1);
    model.addAttribute("books", readingList);
    Flux<Recommendation> recommendations = getRecommendations();
    IReactiveDataDriverContextVariable recommendationsReactiveDataDrivenMode =
        new ReactiveDataDriverContextVariable(getRecommendations(), 1);
    model.addAttribute("recommendations", getRecommendations());
    return "readingList";
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public String addToReadingList(Book book) {
    book.setReader("Tella");
    readingListRepository.save(book).subscribe();
    return "redirect:/readingList";
  }

  @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
  public String deleteFromReadingList(@PathVariable String id) {
    readingListRepository.deleteById(id).subscribe();
    return "redirect:/readingList";
  }

  @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
  public String editReadingListView(@PathVariable String id, Model model) {
    Mono<Book> book = readingListRepository.findById(id);
    model.addAttribute("book", book);
    return "editReadingList";
  }

  @RequestMapping(value = "/edit", method = RequestMethod.POST)
  public String editReadingListItem(Book updatedBook) {
    readingListRepository
        .findById(updatedBook.getId())
        .flatMap(
            book -> {
              book.setTitle(updatedBook.getTitle());
              book.setAuthor(updatedBook.getAuthor());
              book.setIsbn(updatedBook.getIsbn());
              book.setDescription(updatedBook.getDescription());
              return readingListRepository.save(book);
            })
        .subscribe();
    return "redirect:/readingList";
  }

  public Flux<Recommendation> getRecommendations() {
    return this.webClient.get().uri("recommendations").retrieve().bodyToFlux(Recommendation.class);
  }
}
