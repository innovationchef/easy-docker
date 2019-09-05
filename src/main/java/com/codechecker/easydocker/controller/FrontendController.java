package com.codechecker.easydocker.controller;

import com.codechecker.easydocker.model.Python;
import com.codechecker.easydocker.service.PythonConfigFIles;
import com.codechecker.easydocker.utils.MediaTypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
public class FrontendController {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private PythonConfigFIles pythonConfigFIles;

    @GetMapping(value = "/python")
    public String python(Model model) {
        List<String> versions = Arrays.asList("2.7", "3.0", "3.3");
        List<String> packages = Arrays.asList("numpy==1.16.*", "matplotlib==3.*", "seaborn==0.8.1", "pandas");

        Python python = new Python();
        python.setVersions(versions);
        python.setPackages(packages);

        model.addAttribute("python", python);
        return "Python";
    }

    @PostMapping(value = "/python")
    public String processForm(@ModelAttribute(value="python") Python python) {
        System.out.println(python.getSelectedVersion());
        System.out.println(python.getSelectedPackages());
        return "index";
    }

    @GetMapping(value = "/r")
    public String r(Model model) {
        return "R";
    }

    @GetMapping(value = "/julia")
    public String julia(Model model) {
        return "Julia";
    }

    @PostMapping("/download")
    public ResponseEntity<ByteArrayResource> downloadFile(
            @RequestParam(defaultValue = "r2d_python.txt") String fileName, @ModelAttribute(value="python") Python python) throws IOException {

        byte[] byteArray = pythonConfigFIles.createRequirementsTxt(python.getSelectedPackages()).getBytes();
        ByteArrayResource resource = new ByteArrayResource(byteArray);
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=r2d_python.txt")
                .contentType(mediaType)
                .contentLength(byteArray.length)
                .body(resource);
    }

}