package com.example.toyproject_shoppingmall.entity;


import com.example.toyproject_shoppingmall.constant.Role;
import com.example.toyproject_shoppingmall.dto.PassChangeDTO;
import com.example.toyproject_shoppingmall.dto.UserFormDTO;
import com.example.toyproject_shoppingmall.dto.UserPasswordDTO;
import com.example.toyproject_shoppingmall.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Setter
@ToString
@Table(name = "shopuser")
public class ShopUser extends BaseEntity {
    private static final Logger log = LoggerFactory.getLogger(ShopUser.class);
    @Id
    @Column(name = "shopuser_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;  //고유 참조 키

    @Column(nullable = false, unique = true)
    private String loginId; //아이디

    @Column(nullable = false)
    private String password;   //비밀번호product

    @Column(nullable = false)
    private String name;       //유저의 이름


    @Column(unique = true, nullable = false)
    private String email;      //이메일

    @Column(nullable = false, unique = true)
    private String tel;        //전화번호

    @Column(nullable = false)
    private String address;    //주소

    @Enumerated(EnumType.STRING)
    private Role role;              //권한 설정




    //회원 가입용 메소드 생성
    public static ShopUser createUser(UserFormDTO userFormDTO, PasswordEncoder passwordEncoder) {
        ShopUser shopUser = new ShopUser();

        shopUser.setLoginId(userFormDTO.getLoginId());

        shopUser.setName(userFormDTO.getName());
        shopUser.setEmail(userFormDTO.getEmail());
        shopUser.setTel(userFormDTO.getTel());
        shopUser.setAddress(userFormDTO.getAddress());

        //가입자의 롤을 정하는 메소드
        shopUser.setRole(Role.ADMIN); //어드민 생성
        //shopUser.setRole(Role.USER);  // 일반유저 생`성

        //패스워드의 암호와 작업 >DTO로 받은 패스워드를 passwordEncoder로 담아 객체로 저장 후 Entity에 저장
        String password = passwordEncoder.encode(userFormDTO.getPassword());
        shopUser.setPassword(password);

        return shopUser;
    }

    public void modifyUser(UserFormDTO userFormDTO) {
        if (userFormDTO.getName() != null) {
            this.setName(userFormDTO.getName());
        }
        if (userFormDTO.getAddress() != null) {
            this.setAddress(userFormDTO.getAddress());
        }
        if (userFormDTO.getTel() != null) {
            this.setTel(userFormDTO.getTel());
        }
    }

    public void updatePassword(UserPasswordDTO userPasswordDTO, PasswordEncoder passwordEncoder) {

        String newPassword  = passwordEncoder.encode(userPasswordDTO.getNewPassword());

        this.password = newPassword;

    }

    public boolean passChange(PassChangeDTO passChangeDTO, PasswordEncoder passwordEncoder) {
        //passwordEncoder.matches(변경할 비밀번호(DB에입력받은) , 원시(기존) 비밀번호) 잘 모르겠으면 코드를 따라가보자
        boolean ps = passwordEncoder.matches(passChangeDTO.getCurPassword(),this.getPassword());
        if (ps) {

            log.info("값일치" + passChangeDTO.getNewPassword());
            log.info(this.getName());
            this.setName("짱구");
            log.info(this.getName());
            this.setPassword(passwordEncoder.encode(passChangeDTO.getNewPassword()));
            return ps;
        }
        return false;

    }
}
