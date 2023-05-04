package shop.mtcoding.bank.config.jwt;

public interface JwtVO {

    public static final String SECRET = "test"; // HS256 (대칭키), 원래는 SECRET은 노출하면 안 됨
    public static final int EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 일주일
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";
}
