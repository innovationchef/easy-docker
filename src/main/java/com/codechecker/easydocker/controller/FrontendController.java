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
import java.io.File;
import java.io.FileWriter;
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
        List<String> versions = Arrays.asList("python-2.7", "python-3.0", "python-3.3");
        List<String> packages = Arrays.asList("numpy==1.16.*", "matplotlib==3.*", "seaborn==0.8.1", "pandas");

        Python python = new Python();
        python.setVersions(versions);
        python.setPackages(packages);

        model.addAttribute("python", python);
        return "Python";
    }

    @PostMapping(value = "/python")
    public String processForm(@ModelAttribute(value="python") Python python) throws Exception {
        System.out.println(python.getSelectedVersion());
        System.out.println(python.getSelectedPackages());
        System.out.println(python.getPath());

        String text1 = pythonConfigFIles.createRequirementsTxt(python.getSelectedPackages());
        File file1 = new File(python.getPath()+"\\requirements.txt");
        FileWriter fileWriter1 = new FileWriter(file1);
        fileWriter1.write(text1);
        fileWriter1.flush();
        fileWriter1.close();

        String text2 = pythonConfigFIles.createRuntimeTxt(python.getSelectedVersion());
        File file = new File(python.getPath()+"\\runtime.txt");
        FileWriter fileWriter2 = new FileWriter(file);
        fileWriter2.write(text2);
        fileWriter2.flush();
        fileWriter2.close();

        byte[] byteArray = pythonConfigFIles.createRequirementsTxt(python.getSelectedPackages()).getBytes();

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
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam(defaultValue = "requirements.txt") String fileName,
                                                          @ModelAttribute(value="python") Python python) throws IOException {

        byte[] byteArray = pythonConfigFIles.createRequirementsTxt(python.getSelectedPackages()).getBytes();
        ByteArrayResource resource = new ByteArrayResource(byteArray);
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=requirements.txt")
                .contentType(mediaType)
                .contentLength(byteArray.length)
                .body(resource);
    }

}