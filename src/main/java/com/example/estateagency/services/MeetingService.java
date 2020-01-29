package com.example.estateagency.services;

import com.example.estateagency.models.Meeting;

import java.util.List;

public interface MeetingService {
    Meeting save(Meeting meeting);
    List<Meeting> getAllMeetings();
}
