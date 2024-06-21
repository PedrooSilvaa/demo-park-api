package com.mballem.demoparkapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class JasperService {

    private final ResourceLoader resourceLoader;
    private final DataSource dataSource;

    private Map<String, Object> params = new HashMap<>();

    private static final String JASPER_DIRETORIO = "classpath:reports/";

    /**
     * Adiciona parâmetros ao relatório Jasper.
     *
     * @param key   Chave do parâmetro
     * @param value Valor do parâmetro
     */
    public void addParams(String key, Object value) {
        this.params.put("IMAGEM_DIRETORIO", JASPER_DIRETORIO);
        this.params.put("REPORT_LOCALE", new Locale("pt", "BR"));
        this.params.put(key, value);
    }

    /**
     * Gera um arquivo PDF a partir de um relatório Jasper.
     *
     * @return Arquivo PDF gerado como array de bytes
     */
    public byte[] gerarPdf() {
        byte[] bytes = null;
        try {
            Resource resource = resourceLoader.getResource(JASPER_DIRETORIO.concat("estacionamentos.jasper"));
            InputStream stream = resource.getInputStream();
            JasperPrint print = JasperFillManager.fillReport(stream, params, dataSource.getConnection());
            bytes = JasperExportManager.exportReportToPdf(print); // Exporta o relatório para PDF
        } catch (IOException | JRException | SQLException e) {
            log.error("Jasper Reports ::: ", e.getCause()); // Log de erro caso ocorra alguma exceção
            throw new RuntimeException(e); // Lança uma exceção em caso de erro
        }
        return bytes; // Retorna o array de bytes do PDF gerado
    }
}
