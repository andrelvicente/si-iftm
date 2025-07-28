package org.iftm.gerenciadorveterinarios.selenium;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes automatizados para a pesquisa de veterinários usando Selenium
 * WebDriver
 * 
 * Cenários testados:
 * 1. Pesquisar veterinário por nome existente
 * 2. Pesquisar veterinário por nome inexistente
 * 3. Realizar pesquisa com campo vazio
 * 4. Verificar elementos da página de pesquisa
 */
class PesquisarVeterinarioSeleniumTest extends BaseSeleniumTest {

    @Test
    @DisplayName("Cenário 1: Pesquisar veterinário por nome existente")
    void testPesquisarVeterinarioPorNomeExistente() {
        // DEFINIÇÃO DO CENÁRIO
        String nomePesquisa = "Dr. João";

        // CHAMADA DA AÇÃO
        // 1. Navegar para a página inicial
        navegarParaHome();

        // 2. Clicar no botão "Consultar"
        WebElement botaoConsultar = driver.findElement(By.linkText("Consultar"));
        botaoConsultar.click();

        // 3. Aguardar carregamento da página de pesquisa
        assertTrue(driver.getCurrentUrl().contains("/find"),
                "Deveria estar na página de pesquisa");

        // 4. Preencher o campo de pesquisa
        WebElement campoNome = driver.findElement(By.id("nome"));
        campoNome.clear();
        campoNome.sendKeys(nomePesquisa);

        // 5. Clicar no botão "Consultar"
        WebElement botaoPesquisar = driver.findElement(By.xpath("//button[@type='submit' and text()='Consultar']"));
        botaoPesquisar.click();

        // VERIFICAÇÃO DA RESPOSTA
        // 1. Verificar se foi redirecionado para a página de resultados
        assertTrue(driver.getCurrentUrl().contains("/home"),
                "Deveria ter sido redirecionado para a página inicial com resultados");

        // 2. Verificar se existe uma tabela de resultados
        WebElement tabelaVeterinarios = driver.findElement(By.className("table"));
        assertNotNull(tabelaVeterinarios, "Deveria existir uma tabela de veterinários");

        // 3. Verificar se a tabela contém cabeçalhos corretos
        String textoTabela = tabelaVeterinarios.getText();
        assertTrue(textoTabela.contains("Nome"), "Tabela deveria conter coluna 'Nome'");
        assertTrue(textoTabela.contains("Especialidade"), "Tabela deveria conter coluna 'Especialidade'");
        assertTrue(textoTabela.contains("Email"), "Tabela deveria conter coluna 'Email'");
        assertTrue(textoTabela.contains("Salario"), "Tabela deveria conter coluna 'Salario'");
    }

    @Test
    @DisplayName("Cenário 2: Pesquisar veterinário por nome inexistente")
    void testPesquisarVeterinarioPorNomeInexistente() {
        // DEFINIÇÃO DO CENÁRIO
        String nomePesquisaInexistente = "Dr. Inexistente XYZ";

        // CHAMADA DA AÇÃO
        // 1. Navegar para a página inicial
        navegarParaHome();

        // 2. Clicar no botão "Consultar"
        WebElement botaoConsultar = driver.findElement(By.linkText("Consultar"));
        botaoConsultar.click();

        // 3. Verificar se chegou na página de pesquisa
        assertTrue(driver.getCurrentUrl().contains("/find"),
                "Deveria estar na página de pesquisa");

        // 4. Preencher o campo de pesquisa com nome inexistente
        WebElement campoNome = driver.findElement(By.id("nome"));
        campoNome.clear();
        campoNome.sendKeys(nomePesquisaInexistente);

        // 5. Clicar no botão "Consultar"
        WebElement botaoPesquisar = driver.findElement(By.xpath("//button[@type='submit' and text()='Consultar']"));
        botaoPesquisar.click();

        // VERIFICAÇÃO DA RESPOSTA
        // 1. Verificar se foi redirecionado para a página inicial
        assertTrue(driver.getCurrentUrl().contains("/home"),
                "Deveria ter sido redirecionado para a página inicial");

        // 2. Verificar se existe uma tabela (mesmo vazia)
        WebElement tabelaVeterinarios = driver.findElement(By.className("table"));
        assertNotNull(tabelaVeterinarios, "Deveria existir uma tabela de veterinários");

        // 3. Verificar se o nome pesquisado não aparece nos resultados
        String textoTabela = tabelaVeterinarios.getText();
        assertFalse(textoTabela.contains(nomePesquisaInexistente),
                "O nome inexistente não deveria aparecer na tabela");
    }

    @Test
    @DisplayName("Cenário 3: Realizar pesquisa com campo vazio")
    void testPesquisaComCampoVazio() {
        // DEFINIÇÃO DO CENÁRIO
        // Realizar pesquisa sem preencher o campo de nome

        // CHAMADA DA AÇÃO
        // 1. Navegar para a página inicial
        navegarParaHome();

        // 2. Clicar no botão "Consultar"
        WebElement botaoConsultar = driver.findElement(By.linkText("Consultar"));
        botaoConsultar.click();

        // 3. Verificar se chegou na página de pesquisa
        assertTrue(driver.getCurrentUrl().contains("/find"),
                "Deveria estar na página de pesquisa");

        // 4. Deixar o campo de pesquisa vazio e submeter
        WebElement campoNome = driver.findElement(By.id("nome"));
        campoNome.clear(); // Garantir que está vazio

        // 5. Clicar no botão "Consultar"
        WebElement botaoPesquisar = driver.findElement(By.xpath("//button[@type='submit' and text()='Consultar']"));
        botaoPesquisar.click();

        // VERIFICAÇÃO DA RESPOSTA
        // 1. Verificar se foi redirecionado para a página inicial
        assertTrue(driver.getCurrentUrl().contains("/home"),
                "Deveria ter sido redirecionado para a página inicial");

        // 2. Verificar se todos os veterinários são listados (pesquisa vazia = listar
        // todos)
        WebElement tabelaVeterinarios = driver.findElement(By.className("table"));
        assertNotNull(tabelaVeterinarios, "Deveria existir uma tabela de veterinários");

        // 3. Verificar se existe pelo menos o cabeçalho da tabela
        String textoTabela = tabelaVeterinarios.getText();
        assertTrue(textoTabela.contains("Nome"), "Tabela deveria conter coluna 'Nome'");
        assertTrue(textoTabela.contains("Especialidade"), "Tabela deveria conter coluna 'Especialidade'");
    }

    @Test
    @DisplayName("Cenário 4: Verificar elementos da página de pesquisa")
    void testVerificarElementosPaginaPesquisa() {
        // DEFINIÇÃO DO CENÁRIO
        // Verificar se todos os elementos necessários estão presentes na página de
        // pesquisa

        // CHAMADA DA AÇÃO
        // 1. Navegar para a página inicial
        navegarParaHome();

        // 2. Clicar no botão "Consultar"
        WebElement botaoConsultar = driver.findElement(By.linkText("Consultar"));
        botaoConsultar.click();

        // VERIFICAÇÃO DA RESPOSTA
        // 1. Verificar se chegou na página de pesquisa
        assertTrue(driver.getCurrentUrl().contains("/find"),
                "Deveria estar na página de pesquisa");

        // 2. Verificar se existe o título da página
        WebElement titulo = driver.findElement(By.xpath("//h1[contains(text(), 'Pesquisar veterinários')]"));
        assertNotNull(titulo, "Deveria existir um título 'Pesquisar veterinários'");
        assertEquals("Pesquisar veterinários", titulo.getText(),
                "O título deveria ser 'Pesquisar veterinários'");

        // 3. Verificar se existe o campo de nome
        WebElement campoNome = driver.findElement(By.id("nome"));
        assertNotNull(campoNome, "Deveria existir um campo de nome");
        assertEquals("text", campoNome.getAttribute("type"),
                "Campo nome deveria ser do tipo 'text'");
        assertEquals("Nome Completo", campoNome.getAttribute("placeholder"),
                "Campo nome deveria ter placeholder 'Nome Completo'");

        // 4. Verificar se existe o label do campo
        WebElement labelNome = driver.findElement(By.xpath("//label[@for='nome']"));
        assertNotNull(labelNome, "Deveria existir um label para o campo nome");
        assertEquals("Nome", labelNome.getText(),
                "O label deveria ter texto 'Nome'");

        // 5. Verificar se existe o botão de consultar
        WebElement botaoPesquisar = driver.findElement(By.xpath("//button[@type='submit' and text()='Consultar']"));
        assertNotNull(botaoPesquisar, "Deveria existir um botão 'Consultar'");
        assertEquals("submit", botaoPesquisar.getAttribute("type"),
                "Botão deveria ser do tipo 'submit'");

        // 6. Verificar se o campo está inicialmente vazio
        assertEquals("", campoNome.getAttribute("value"),
                "Campo nome deveria estar inicialmente vazio");

        // 7. Verificar se o formulário tem action correto
        WebElement formulario = driver.findElement(By.xpath("//form"));
        assertNotNull(formulario, "Deveria existir um formulário");
        String action = formulario.getAttribute("action");
        assertTrue(action.contains("/home") || action.isEmpty(),
                "Formulário deveria ter action apontando para /home ou estar vazio");
    }

    @Test
    @DisplayName("Cenário 5: Verificar funcionalidade de pesquisa parcial")
    void testPesquisaParcial() {
        // DEFINIÇÃO DO CENÁRIO
        String pesquisaParcial = "Dr";

        // CHAMADA DA AÇÃO
        // 1. Navegar para a página inicial
        navegarParaHome();

        // 2. Clicar no botão "Consultar"
        WebElement botaoConsultar = driver.findElement(By.linkText("Consultar"));
        botaoConsultar.click();

        // 3. Preencher o campo com pesquisa parcial
        WebElement campoNome = driver.findElement(By.id("nome"));
        campoNome.clear();
        campoNome.sendKeys(pesquisaParcial);

        // 4. Submeter o formulário
        WebElement botaoPesquisar = driver.findElement(By.xpath("//button[@type='submit' and text()='Consultar']"));
        botaoPesquisar.click();

        // VERIFICAÇÃO DA RESPOSTA
        // 1. Verificar se foi redirecionado para a página inicial
        assertTrue(driver.getCurrentUrl().contains("/home"),
                "Deveria ter sido redirecionado para a página inicial");

        // 2. Verificar se existe uma tabela de resultados
        WebElement tabelaVeterinarios = driver.findElement(By.className("table"));
        assertNotNull(tabelaVeterinarios, "Deveria existir uma tabela de veterinários");

        // 3. A verificação específica de resultados depende da implementação do backend
        // Aqui verificamos apenas que a pesquisa foi executada sem erro
        String textoTabela = tabelaVeterinarios.getText();
        assertTrue(textoTabela.contains("Nome") && textoTabela.contains("Especialidade"),
                "Tabela deveria conter as colunas esperadas");
    }
}
