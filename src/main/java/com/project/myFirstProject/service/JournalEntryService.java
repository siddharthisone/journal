package com.project.myFirstProject.service;


import com.project.myFirstProject.entity.JournalEntry;
import com.project.myFirstProject.entity.User;
import com.project.myFirstProject.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Component
public class JournalEntryService {

@Autowired
    private JournalEntryRepo journalEntryRepo;

@Autowired
private UserService userService;

@Transactional
public void saveEntry(JournalEntry journalEntry, String username)
{
    try {
        User user = userService.findByUsername(username);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry save = journalEntryRepo.save(journalEntry);
        user.getJournalEntries().add(save);
        userService.saveUser(user);
       }
    catch (Exception e)
    {
        System.out.println(e);
        throw new RuntimeException("Error saving entry "+e);
    }

}
    public void saveEntry(JournalEntry journalEntry)
    {
        journalEntryRepo.save(journalEntry);
    }

public List<JournalEntry> getAll()
{
      return journalEntryRepo.findAll();
}

public Optional<JournalEntry> findById(ObjectId objectId)
{
    return journalEntryRepo.findById(objectId);
}

@Transactional
public boolean deleteById(ObjectId objectId, String username)
{

    boolean removed = false;
    try {
        User user = userService.findByUsername(username);
        removed = user.getJournalEntries().removeIf(x -> x.getId().equals(objectId));
        if (removed) {
            userService.saveUser(user); //user updated
            journalEntryRepo.deleteById(objectId);
        }
    }
    catch(Exception e)
    {
        throw  new RuntimeException("An error occurred while deleting the entry",e);
    }
return removed;

}

//public List<JournalEntry> findByUsername(String username)
//{
//return
//}



}
