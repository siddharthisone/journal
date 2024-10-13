package com.project.myFirstProject.controller;


import com.project.myFirstProject.entity.JournalEntry;
import com.project.myFirstProject.entity.User;
import com.project.myFirstProject.service.JournalEntryService;
import com.project.myFirstProject.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController2{

    //we will write end points as methods
    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<?> getAllJournalEntriesOfUsers() //localhost:8080/journal //GET
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> all = user.getJournalEntries();

        if(all!=null && !all.isEmpty())
        {
       return new ResponseEntity<>(all, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("id/{objectId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId objectId)
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
          User user =  userService.findByUsername(username);
        List<JournalEntry> journalEntryList = user.getJournalEntries().stream().filter(x -> x.getId().equals(objectId)).collect(Collectors.toList());

        if (!journalEntryList.isEmpty())
        {
        Optional<JournalEntry> journalEntry =  journalEntryService.findById(objectId);

        if (journalEntry.isPresent())
        {
            return new ResponseEntity<JournalEntry>(journalEntry.get(), HttpStatus.OK);
        }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) //localhost:8080/journal //POST
    {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
        try {
            journalEntryService.saveEntry(myEntry,username);
            return new ResponseEntity<JournalEntry>(myEntry, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("id/{objectId}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId objectId) //wildcard pattern
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean removed = journalEntryService.deleteById(objectId,username);
        if (removed)
        {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("id/{objectId}")
    public ResponseEntity<?> updateJournalById(
            @PathVariable ObjectId objectId,
            @RequestBody JournalEntry newEntry
            )
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByUsername(username);
        List<JournalEntry> collect = user.getJournalEntries().stream()
                .filter(x -> x.getId().equals(objectId)).collect(Collectors.toList());

        if (!collect.isEmpty())
        {
            Optional<JournalEntry> oldJournalEntry = journalEntryService.findById(objectId);

            if (oldJournalEntry.isPresent())
            {
                JournalEntry old = oldJournalEntry.get();
                old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}
