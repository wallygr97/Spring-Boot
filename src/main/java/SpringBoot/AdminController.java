package SpringBoot;

import SpringBoot.entidades.Familia;
import SpringBoot.entidades.Rol;
import SpringBoot.entidades.SubFamilia;
import SpringBoot.entidades.Usuario;
import SpringBoot.servicios.FamiliaServices;
import SpringBoot.servicios.RolServices;
import SpringBoot.servicios.SubFamiliaServices;
import SpringBoot.servicios.UsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;


@Controller()
@RequestMapping("/zona_admin")
public class AdminController {
    @Autowired
    private MessageSource messageSources;

    @Autowired
    private UsuarioServices usuarioServices;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private FamiliaServices familiaServices;
    @Autowired
    private SubFamiliaServices subFamiliaServices;

    @Autowired
    private RolServices rolServices;

    @RequestMapping("/")
    public String verUsuarios(Model model){

        ArrayList<Usuario> usuarios = new ArrayList<>();
        for(Usuario usuario: usuarioServices.todosUsuarios()){
            usuario.setRoles(rolServices.rolesUsuario(usuario.getUsername()));
            usuarios.add(usuario);
        }
        model.addAttribute("usuarios",usuarios);
        return "/ver_usuario";
    }

    @RequestMapping("/familias/")
    public String verFamilias(Model model){
        model.addAttribute("familias", familiaServices.todasFamilias());
        return "/ver_familias";
    }

     @RequestMapping("/familias/crear_familia/")
    public String crearFamilia(Model model){
        model.addAttribute("familia",new Familia());
        return "/crear_familia";
    }


    @PostMapping("/familias/crear_familia/")
    public String crearFamiliaPost(@ModelAttribute Familia familia){
        familiaServices.creacionFamilia(familia);
        return "redirect:/zona_admin/familias/";
    }

    @RequestMapping("/familias/editar_familia")
    public String editarFamilia(Model model,@RequestParam("id") int id){
        Familia familia = familiaServices.getFamilia(id);
        model.addAttribute("familia",familia);
        model.addAttribute("sub_familias",subFamiliaServices.subFamiliasFamilia(familia));
        return "/editar_familia";
    }

    @PostMapping("/familias/editar_familia")
    public String guardarFamiliaEditada(@RequestParam("familia_id") int id, @RequestParam("nombre") String subFamilia){
        Familia familia = familiaServices.getFamilia(id);
        SubFamilia sub = new SubFamilia();
        sub.setNombre(subFamilia);
        sub.setFamilia(familia);
        subFamiliaServices.cracionSubFamilia(sub);
        return "redirect:/zona_admin/familias/editar_familia?id="+ familia.getId();
    }
    @Secured("ROLE_ADMIN")
    @RequestMapping("/crear_usuario/")
    public String crearUsuario(Model model){
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("allRoles", rolServices.todosRoles());
        return "/crear_usuario";
    }

    @PostMapping("/crear_usuario/")
    @Transactional
    public String crearUsuarioPost(
            @RequestParam("roles") String[] roles, @RequestParam("username") String usuario,
            @RequestParam("password") String password, @RequestParam("nombre") String nombre,
            @RequestParam("apellido") String apellido
            ){
        Usuario u = new Usuario();
        u.setUsername(usuario);
        u.setNombre(nombre);
        u.setApellido(apellido);
        u.setPassword(bCryptPasswordEncoder.encode(password));
        usuarioServices.crearUsuario(u);

        for(String rol: roles){
            Rol r = new Rol();
            r.setRol(rol);
            r.setUsuario(u.getUsername());
            rolServices.creacionRol(r);
        }

        return "redirect:/zona_admin/";
    }

    @RequestMapping("/crear_rol/")
    public String crearRol(Model model){
        model.addAttribute("rol", new Rol());
        return "crear_rol";
    }

    @PostMapping("/crear_rol/")
    public String crearRolPost(@ModelAttribute Rol rol){

        return "lol";
    }





}
