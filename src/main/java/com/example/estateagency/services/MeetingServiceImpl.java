package com.example.estateagency.services;

import com.example.estateagency.models.Meeting;
import com.example.estateagency.repositories.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingServiceImpl implements MeetingService  {

    @Autowired
    MeetingRepository meetingRepository;
    @Override
    public Meeting save(Meeting meeting) {
        return meetingRepository.save(meeting);
    }

    @Override
    public List<Meeting> getAllMeetings() {
        return meetingRepository.findAll();
    }
}
