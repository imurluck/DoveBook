package com.example.dovebook.HandleRequest;

import com.example.dovebook.base.model.Friend;

import java.util.List;

public class ContactRequestManager {
    private static final String TAG = "ContactRequestManager";
    private ContactRequestPresenter mContactRequestPresenter;
    private List<Friend> contactRequestList;

    public ContactRequestManager(ContactRequestPresenter contactRequestPresenter){
        this.mContactRequestPresenter=contactRequestPresenter;
    }

    public void initContactRequestList(List<Friend> list){
        if(list!=null){
            contactRequestList=list;
        }
    }

    public List<Friend>getContactRequestList(){
        return contactRequestList;
    }

    public void deleteRecord(Friend friend){
        if(contactRequestList!=null){
            for(int i=0;i<contactRequestList.size();i++){
                if(contactRequestList.get(i).getUserName().equals(friend.getUserName())){
                    contactRequestList.remove(i);
                    break;
                }
            }
        }
    }

}
