package com.back.salcho.wishlist.controller;


import com.back.salcho.member.entity.MemberEntity;
import com.back.salcho.util.FileEntity;
import com.back.salcho.wishlist.entity.WishListEntity;
import com.back.salcho.wishlist.service.WishListService;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class WishListController {

    @Autowired
    private WishListService wishListService;

    @GetMapping("/api/wish/getWishList")
    @ResponseBody
    public List<Map<String,Object>>  getWishList(@RequestParam String memberId,@RequestParam String cate) {
        System.out.println(memberId);
        WishListEntity entity = new WishListEntity();
        entity.setWishItemCate(cate);
        entity.setMemberId(Integer.parseInt(memberId));
        List<Map<String,Object>> map =  wishListService.getWishList(entity);
        System.out.println(map.toString());
        return map;
    }

    @GetMapping("/api/wish/getCode")
    @ResponseBody
    public List<Map<String,String>>  getWishCode() {
        List<Map<String,String>> map =  wishListService.getWishCode();
        return map;
    }


    @PostMapping("/api/wish/insertWish")
    @ResponseBody
    public Map<String, String> signup( WishListEntity wishListEntity , HttpServletRequest request)  {
        Map<String, String> res = new HashMap<>();
        //업로드된 파일의 정보를 가지고 있는 MultipartFile 객체의 참조값 얻어오기
       String today =new SimpleDateFormat("yyMMdd").format(new Date());
        if(wishListEntity.getImgFile() != null){
            MultipartFile myFile=wishListEntity.getImgFile();
            //원본 파일명
            String orgFileName = myFile.getOriginalFilename();
            String sep = File.separator;
            String filePath= "C:" + sep + "salcho_full" + sep+"front"+ sep+"public"+ sep + "img"+ sep + "wish" + sep + today + sep;

            File upload =new File(filePath);

            if(!upload.exists()) {
                upload.mkdir();
            }
            String saveFileName=  System.currentTimeMillis()+orgFileName;
            String savePath = sep + "img"+ sep +"wish"+ sep + today + sep +saveFileName;
            try {
                //upload 폴더에 파일을 저장한다.
                myFile.transferTo(new File(filePath+saveFileName));
                wishListEntity.setWishItemImgPath(savePath);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(wishListEntity.toString());
        int result = wishListService.insertWishItem(wishListEntity);
        if(result >0){
            res.put("msg","상품이 추가되었습니다.");
            res.put("success","Y");
        }else{
            res.put("msg","상품 추가에 실패하였습니다.");
            res.put("success","N");
        }
        return res;
    }
}