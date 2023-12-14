package gl.bci.ejercicio.auth;

import gl.bci.ejercicio.model.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class JwtUtilTest {

    JwtUtil jwtUtil;

    UserDto userDto;

    long accessTokenValidity = 60*60*1000L;

    String SECRET_KEY = "mysecretkey";

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .email("omar.rebollo@gmail.com")
                .name("omare")
                .role("USER")
                .build();
        jwtUtil = new JwtUtil();
    }

    @Test
    void create_Token() {
        String accessToken = jwtUtil.createToken(userDto);
        String subject = Jwts.parser().setSigningKey(SECRET_KEY)
                     .parseClaimsJws(accessToken)
                     .getBody()
                     .getSubject();
        assertThat(subject, equalTo("omar.rebollo@gmail.com"));
    }

    @Test
    void access_Token_Uses_Issue_Date(){
        String accessToken = jwtUtil.createToken(userDto);
        Date issueAt = Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(accessToken)
                .getBody()
                .getIssuedAt();
        assertThat(issueAt, notNullValue());
    }

    @Test
    void create_Token_With_Expiration_Time(){
        String accessToken = jwtUtil.createToken(userDto);
        Claims expirationTime = Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(accessToken)
                .getBody();
        assertThat(expirationTime.getExpiration().getTime(), notNullValue());
    }

    @Test
    void access_Token_Has_24_Hours_Expiration(){
        String accessToken = jwtUtil.createToken(userDto);
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(accessToken)
                .getBody();
        Date issueDate = claims.getIssuedAt();
        Date expiration = claims.getExpiration();
        Date expectedExpiration = new Date(issueDate.getTime()
                + TimeUnit.MINUTES.toMillis(accessTokenValidity));
        assertThat(expiration.getTime(), equalTo(expectedExpiration.getTime()));
    }

    @Test
    void validate_Claims(){
        String accessToken = jwtUtil.createToken(userDto);
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(accessToken)
                .getBody();
        assertThat(jwtUtil.validateClaims(claims),
                equalTo(claims.getExpiration().after(new Date())));
    }

}