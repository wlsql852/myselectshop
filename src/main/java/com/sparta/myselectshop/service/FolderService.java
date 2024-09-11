package com.sparta.myselectshop.service;

import com.sparta.myselectshop.dto.FolderResponseDto;
import com.sparta.myselectshop.entity.Folder;
import com.sparta.myselectshop.entity.User;
import com.sparta.myselectshop.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {
    private final FolderRepository folderRepository;

    public void addFolders(List<String> folderNames, User user) {
        List<Folder> existsFolderList = folderRepository.findAllByUserAndNameIn(user, folderNames);
        List<Folder> folderList = new ArrayList<>();
        for (String folderName : folderNames) {
            if (!isExistFolderName(folderName, existsFolderList)) {
                Folder folder = new Folder(folderName, user);
                folderList.add(folder);
            } else {  //하나라도 중복이면 저장되지 않고 오류 발생
                throw new IllegalArgumentException("폴더명이 중복되었습니다.");
            }
        }
        folderRepository.saveAll(folderList);
    }

    public List<FolderResponseDto> getFolders(User user) {
        List<Folder> folderList = folderRepository.findAllByUser(user);
        List<FolderResponseDto> responseDtoList = new ArrayList<>();
        for (Folder folder : folderList) {
            responseDtoList.add(new FolderResponseDto(folder));
        }
        return responseDtoList;
    }

    private boolean isExistFolderName(String folderName, List<Folder> existsFolderList) {
        for (Folder existFolder : existsFolderList) {
            if(folderName.equals(existFolder.getName())) {
                return true;  //하나라도 중복 있음
            }
        }
        return false;  //일치하는게 하나도 없음
    }
}
