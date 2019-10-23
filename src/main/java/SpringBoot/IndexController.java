package SpringBoot;


import SpringBoot.entidades.*;
import SpringBoot.servicios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;


@Controller()
@RequestMapping("/")
public class IndexController {

    @Autowired
    private UsuarioServices usuarioServices;

    @Autowired
    RolServices rolServices;

    @RequestMapping(value ={"", "/", "/home"})
    public String getLoginPage(Model model, HttpServletRequest request, Principal principal) {
        //String username = request.getSession().getAttribute("username").toString();
        Boolean isAdmin = false;
        HttpSession session = request.getSession();

        List<Rol> rols = rolServices.rolesUsuario(principal.getName());
        for(Rol rol: rols){
            if(rol.getRol().equals("ROLE_ADMIN")){
                isAdmin= true;
            }
        }
        session.setAttribute("isAdmin", isAdmin);
        model.addAttribute("username", principal.getName());
        model.addAttribute("usuario", isAdmin);
        return "/indice";
    }



}
