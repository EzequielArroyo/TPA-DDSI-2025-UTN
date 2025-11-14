package ar.edu.utn.frba.dds.webclient.service.imp;

import ar.edu.utn.frba.dds.webclient.dto.SolicitudDTO;
import ar.edu.utn.frba.dds.webclient.service.ISolicitudService;
import ar.edu.utn.frba.dds.webclient.service.internal.WebApiCallerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Service
public class SolicitudService implements ISolicitudService {

    private final WebApiCallerService webApiCallerService;
    private final String agregadorUrl; // API de usuarios

    public SolicitudService(@Value("${api.backend.url}") String agregadorUrl,
                            WebApiCallerService webApiCallerService) {
        this.webApiCallerService = webApiCallerService;
        this.agregadorUrl = agregadorUrl;
    }

    @Override
    public List<SolicitudDTO> obtenerTodas() {
        return webApiCallerService.get(
                agregadorUrl + "/api/solicitudes",
                List.class
        );
    }


    @Override
    public void aprobarSolicitud(Long id) {
        webApiCallerService.post(
                agregadorUrl + "/api/solicitudes/" + id + "/aprobar",
                null,
                Void.class
        );
    }

    @Override
    public void denegarSolicitud(Long id) {
        webApiCallerService.post(
                agregadorUrl + "/api/solicitudes/" + id + "/denegar",
                null,
                Void.class
        );
    }

    public void crearSolicitud(SolicitudDTO solicitudDTO) {
        try {
            String accessToken = null;
            var attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            accessToken = (String) request.getSession().getAttribute("accessToken");

            if (accessToken != null) {
                // Llamada al API con token
                webApiCallerService.post(agregadorUrl + "/api/solicitudes", solicitudDTO, String.class);
            } else {
                // Llamada al API sin token
                crearSolicitudSinToken(solicitudDTO);
            }
        } catch (Exception e) {
            throw new RuntimeException("No se pudo crear la solicitud", e);
        }
    }

    /**
     * Método fallback si no hay token
     */
    private void crearSolicitudSinToken(SolicitudDTO solicitudDTO) {
        try {
            webApiCallerService.post(agregadorUrl + "/api/solicitudes/public", solicitudDTO, String.class);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo crear la solicitud sin token", e);
        }
    }

}
