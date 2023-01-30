
package com.javadeveloperzone.config.securityHandler;

/*
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationProvider.class);

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String id = authentication.getName();
        String password = (String) authentication.getCredentials();

        logger.info("---------------------login request id=" + id);

        Map<String, Object> reqMap = new HashMap<String,Object>();
        reqMap.put("userid", id);

        CustomUserDetails details = userService.loadUserByUsername(id);

        if (null == details.getPassword() || !password.equals(details.getPassword())) {
            reqMap.put("falsePwd", details.getFalsePwd());
            userService.updateFalsePwd(reqMap);
            throw new BadCredentialsException("");
        }

        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
        result.setDetails(details);

        SecurityContextHolder.getContext().setAuthentication(result);

        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}*/
