package org.iftm.gerenciadorveterinarios.selenium;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes automatizados para listar veterinários usando Selenium WebDriver
 * 
 * Cenários testados:
 * 1. Verificar estrutura da tabela de listagem
 * 2. Verificar conteúdo dos cabeçalhos da tabela
 * 3. Verificar listagem de veterinários cadastrados
 * 4. Verificar comportamento com lista vazia
 * 5. Verificar formatação de dados na tabela
 */
class ListarVeterinarioSeleniumTest extends BaseSeleniumTest {

    @Test
    @DisplayName("Cenário 1: Verificar estrutura da tabela de listagem")
    void testEstruturaTabela() {
        // DEFINIÇÃO DO CENÁRIO
        // Verificar se a página inicial possui uma tabela corretamente estruturada

        // CHAMADA DA AÇÃO
        // 1. Navegar para a página inicial
        navegarParaHome();

        // VERIFICAÇÃO DA RESPOSTA
        // 1. Verificar se existe uma tabela na página
        WebElement tabela = driver.findElement(By.className("table"));
        assertNotNull(tabela, "Deveria existir uma tabela na página inicial");

        // 2. Verificar se a tabela tem a classe CSS correta
        assertTrue(tabela.getAttribute("class").contains("table"),
                "Tabela deveria ter classe 'table'");
        assertTrue(tabela.getAttribute("class").contains("table-light"),
                "Tabela deveria ter classe 'table-light'");

        // 3. Verificar se existe um cabeçalho da tabela
        WebElement cabecalho = driver.findElement(By.xpath("//tr[@class='table-dark']"));
        assertNotNull(cabecalho, "Deveria existir um cabeçalho da tabela");

        // 4. Verificar se o cabeçalho tem a classe CSS correta
        assertEquals("table-dark", cabecalho.getAttribute("class"),
                "Cabeçalho deveria ter classe 'table-dark'");
    }

    @Test
    @DisplayName("Cenário 2: Verificar conteúdo dos cabeçalhos da tabela")
    void testCabecalhosTabela() {
        // DEFINIÇÃO DO CENÁRIO
        // Verificar se todos os cabeçalhos esperados estão presentes na tabela

        // CHAMADA DA AÇÃO
        // 1. Navegar para a página inicial
        navegarParaHome();

        // VERIFICAÇÃO DA RESPOSTA
        // 1. Verificar se existem os cabeçalhos corretos
        WebElement cabecalhoNome = driver.findElement(By.xpath("//th[text()='Nome']"));
        assertNotNull(cabecalhoNome, "Deveria existir cabeçalho 'Nome'");
        assertEquals("Nome", cabecalhoNome.getText(), "Cabeçalho deveria ter texto 'Nome'");

        WebElement cabecalhoEspecialidade = driver.findElement(By.xpath("//th[text()='Especialidade']"));
        assertNotNull(cabecalhoEspecialidade, "Deveria existir cabeçalho 'Especialidade'");
        assertEquals("Especialidade", cabecalhoEspecialidade.getText(),
                "Cabeçalho deveria ter texto 'Especialidade'");

        WebElement cabecalhoEmail = driver.findElement(By.xpath("//th[text()='Email']"));
        assertNotNull(cabecalhoEmail, "Deveria existir cabeçalho 'Email'");
        assertEquals("Email", cabecalhoEmail.getText(), "Cabeçalho deveria ter texto 'Email'");

        WebElement cabecalhoSalario = driver.findElement(By.xpath("//th[text()='Salario']"));
        assertNotNull(cabecalhoSalario, "Deveria existir cabeçalho 'Salario'");
        assertEquals("Salario", cabecalhoSalario.getText(), "Cabeçalho deveria ter texto 'Salario'");

        // 2. Verificar se os cabeçalhos estão na ordem correta
        List<WebElement> cabeçalhos = driver.findElements(By.xpath("//tr[@class='table-dark']/th"));
        assertTrue(cabeçalhos.size() >= 5, "Deveria haver pelo menos 5 colunas (incluindo colunas vazias e ações)");
    }

    @Test
    @DisplayName("Cenário 3: Verificar listagem de veterinários cadastrados")
    void testListagemVeterinarios() {
        // DEFINIÇÃO DO CENÁRIO
        // Cadastrar alguns veterinários e verificar se aparecem na listagem
        String nome1 = "Dr. João da Silva";
        String email1 = "joao@vet.com";
        String especialidade1 = "Cirurgia";
        String salario1 = "5000.00";

        String nome2 = "Dra. Maria Santos";
        String email2 = "maria@vet.com";
        String especialidade2 = "Clínica Geral";
        String salario2 = "4500.00";

        // CHAMADA DA AÇÃO
        // 1. Navegar para a página inicial
        navegarParaHome();

        // 2. Cadastrar primeiro veterinário
        WebElement botaoAdicionar = driver.findElement(By.linkText("Adicionar"));
        botaoAdicionar.click();

        WebElement campoNome = driver.findElement(By.id("nome"));
        campoNome.sendKeys(nome1);

        WebElement campoEmail = driver.findElement(By.id("inputEmail"));
        campoEmail.sendKeys(email1);

        WebElement campoEspecialidade = driver.findElement(By.id("inputEspecialidade"));
        campoEspecialidade.sendKeys(especialidade1);

        WebElement campoSalario = driver.findElement(By.id("inputSalario"));
        campoSalario.sendKeys(salario1);

        WebElement botaoCadastrar = driver.findElement(By.xpath("//button[@type='submit' and text()='Cadastrar']"));
        botaoCadastrar.click();

        // 3. Cadastrar segundo veterinário
        WebElement botaoAdicionar2 = driver.findElement(By.linkText("Adicionar"));
        botaoAdicionar2.click();

        WebElement campoNome2 = driver.findElement(By.id("nome"));
        campoNome2.sendKeys(nome2);

        WebElement campoEmail2 = driver.findElement(By.id("inputEmail"));
        campoEmail2.sendKeys(email2);

        WebElement campoEspecialidade2 = driver.findElement(By.id("inputEspecialidade"));
        campoEspecialidade2.sendKeys(especialidade2);

        WebElement campoSalario2 = driver.findElement(By.id("inputSalario"));
        campoSalario2.sendKeys(salario2);

        WebElement botaoCadastrar2 = driver.findElement(By.xpath("//button[@type='submit' and text()='Cadastrar']"));
        botaoCadastrar2.click();

        // 4. Voltar para a página inicial
        navegarParaHome();

        // VERIFICAÇÃO DA RESPOSTA
        // 1. Verificar se ambos os veterinários aparecem na tabela
        WebElement tabela = driver.findElement(By.className("table"));
        String textoTabela = tabela.getText();

        assertTrue(textoTabela.contains(nome1), "Primeiro veterinário deveria aparecer na tabela");
        assertTrue(textoTabela.contains(email1), "Email do primeiro veterinário deveria aparecer na tabela");
        assertTrue(textoTabela.contains(especialidade1),
                "Especialidade do primeiro veterinário deveria aparecer na tabela");
        assertTrue(textoTabela.contains("R$" + salario1), "Salário do primeiro veterinário deveria aparecer na tabela");

        assertTrue(textoTabela.contains(nome2), "Segundo veterinário deveria aparecer na tabela");
        assertTrue(textoTabela.contains(email2), "Email do segundo veterinário deveria aparecer na tabela");
        assertTrue(textoTabela.contains(especialidade2),
                "Especialidade do segundo veterinário deveria aparecer na tabela");
        assertTrue(textoTabela.contains("R$" + salario2), "Salário do segundo veterinário deveria aparecer na tabela");

        // 2. Verificar se existem linhas de dados na tabela (além do cabeçalho)
        List<WebElement> linhasTabela = driver.findElements(By.xpath("//table//tr[not(@class='table-dark')]"));
        assertTrue(linhasTabela.size() >= 2, "Deveria haver pelo menos 2 linhas de dados na tabela");
    }

    @Test
    @DisplayName("Cenário 4: Verificar comportamento com lista vazia")
    void testListaVazia() {
        // DEFINIÇÃO DO CENÁRIO
        // Verificar como a aplicação se comporta quando não há veterinários cadastrados

        // CHAMADA DA AÇÃO
        // 1. Navegar para a página inicial
        navegarParaHome();

        // 2. Excluir todos os veterinários que possam existir
        boolean continuarExcluindo = true;
        while (continuarExcluindo) {
            try {
                WebElement botaoExcluir = driver.findElement(By.xpath("//a[contains(@class, 'btn-danger')]"));
                botaoExcluir.click();
                Thread.sleep(500); // Aguardar um pouco para a página recarregar
            } catch (Exception e) {
                continuarExcluindo = false;
            }
        }

        // VERIFICAÇÃO DA RESPOSTA
        // 1. Verificar se a tabela ainda existe
        WebElement tabela = driver.findElement(By.className("table"));
        assertNotNull(tabela, "Tabela deveria continuar existindo mesmo sem dados");

        // 2. Verificar se os cabeçalhos ainda estão visíveis
        String textoTabela = tabela.getText();
        assertTrue(textoTabela.contains("Nome"), "Cabeçalho 'Nome' deveria continuar visível");
        assertTrue(textoTabela.contains("Especialidade"), "Cabeçalho 'Especialidade' deveria continuar visível");
        assertTrue(textoTabela.contains("Email"), "Cabeçalho 'Email' deveria continuar visível");
        assertTrue(textoTabela.contains("Salario"), "Cabeçalho 'Salario' deveria continuar visível");

        // 3. Verificar se não há linhas de dados (apenas cabeçalho)
        List<WebElement> linhasDados = driver.findElements(By.xpath("//table//tr[not(@class='table-dark')]"));
        assertEquals(0, linhasDados.size(), "Não deveria haver linhas de dados quando a lista está vazia");
    }

    @Test
    @DisplayName("Cenário 5: Verificar formatação de dados na tabela")
    void testFormatacaoDados() {
        // DEFINIÇÃO DO CENÁRIO
        String nome = "Dr. Formato Teste";
        String email = "formato@teste.com";
        String especialidade = "Cardiologia Veterinária";
        String salario = "7500.50";

        // CHAMADA DA AÇÃO
        // 1. Navegar para a página inicial
        navegarParaHome();

        // 2. Cadastrar um veterinário
        WebElement botaoAdicionar = driver.findElement(By.linkText("Adicionar"));
        botaoAdicionar.click();

        WebElement campoNome = driver.findElement(By.id("nome"));
        campoNome.sendKeys(nome);

        WebElement campoEmail = driver.findElement(By.id("inputEmail"));
        campoEmail.sendKeys(email);

        WebElement campoEspecialidade = driver.findElement(By.id("inputEspecialidade"));
        campoEspecialidade.sendKeys(especialidade);

        WebElement campoSalario = driver.findElement(By.id("inputSalario"));
        campoSalario.sendKeys(salario);

        WebElement botaoCadastrar = driver.findElement(By.xpath("//button[@type='submit' and text()='Cadastrar']"));
        botaoCadastrar.click();

        // 3. Voltar para a página inicial
        navegarParaHome();

        // VERIFICAÇÃO DA RESPOSTA
        // 1. Verificar se o salário está formatado corretamente com R$
        WebElement tabela = driver.findElement(By.className("table"));
        String textoTabela = tabela.getText();
        assertTrue(textoTabela.contains("R$" + salario),
                "Salário deveria estar formatado com 'R$' na frente");

        // 2. Verificar se os dados estão organizados corretamente nas colunas
        // Procurar pela linha que contém o nome do veterinário cadastrado
        WebElement linhaDados = driver.findElement(By.xpath("//tr[contains(td, '" + nome + "')]"));
        assertNotNull(linhaDados, "Deveria existir uma linha com os dados do veterinário");

        // 3. Verificar se existem botões de ação na linha
        WebElement botaoEditar = driver.findElement(
                By.xpath("//tr[contains(td, '" + nome + "')]//a[contains(@class, 'btn-warning')]"));
        assertNotNull(botaoEditar, "Deveria existir botão de editar na linha do veterinário");

        WebElement botaoExcluir = driver.findElement(
                By.xpath("//tr[contains(td, '" + nome + "')]//a[contains(@class, 'btn-danger')]"));
        assertNotNull(botaoExcluir, "Deveria existir botão de excluir na linha do veterinário");
    }

    @Test
    @DisplayName("Cenário 6: Verificar título da página")
    void testTituloPagina() {
        // DEFINIÇÃO DO CENÁRIO
        // Verificar se a página inicial possui o título correto

        // CHAMADA DA AÇÃO
        // 1. Navegar para a página inicial
        navegarParaHome();

        // VERIFICAÇÃO DA RESPOSTA
        // 1. Verificar se existe um título na página
        WebElement titulo = driver.findElement(By.xpath("//h1[text()='Veterinarios']"));
        assertNotNull(titulo, "Deveria existir um título 'Veterinarios' na página");
        assertEquals("Veterinarios", titulo.getText(), "Título deveria ser 'Veterinarios'");
    }

    @Test
    @DisplayName("Cenário 7: Verificar botões de navegação")
    void testBotoesNavegacao() {
        // DEFINIÇÃO DO CENÁRIO
        // Verificar se os botões de navegação estão presentes e funcionais

        // CHAMADA DA AÇÃO
        // 1. Navegar para a página inicial
        navegarParaHome();

        // VERIFICAÇÃO DA RESPOSTA
        // 1. Verificar se existe o botão "Adicionar"
        WebElement botaoAdicionar = driver.findElement(By.linkText("Adicionar"));
        assertNotNull(botaoAdicionar, "Deveria existir um botão 'Adicionar'");
        assertTrue(botaoAdicionar.getAttribute("class").contains("btn-dark"),
                "Botão 'Adicionar' deveria ter classe 'btn-dark'");
        assertEquals("/form", botaoAdicionar.getAttribute("href").replace(baseUrl, ""),
                "Botão 'Adicionar' deveria apontar para '/form'");

        // 2. Verificar se existe o botão "Consultar"
        WebElement botaoConsultar = driver.findElement(By.linkText("Consultar"));
        assertNotNull(botaoConsultar, "Deveria existir um botão 'Consultar'");
        assertTrue(botaoConsultar.getAttribute("class").contains("btn-dark"),
                "Botão 'Consultar' deveria ter classe 'btn-dark'");
        assertEquals("/find", botaoConsultar.getAttribute("href").replace(baseUrl, ""),
                "Botão 'Consultar' deveria apontar para '/find'");

        // 3. Testar funcionalidade dos botões clicando neles
        botaoAdicionar.click();
        assertTrue(driver.getCurrentUrl().contains("/form"),
                "Botão 'Adicionar' deveria navegar para a página de formulário");

        navegarParaHome();

        WebElement botaoConsultar2 = driver.findElement(By.linkText("Consultar"));
        botaoConsultar2.click();
        assertTrue(driver.getCurrentUrl().contains("/find"),
                "Botão 'Consultar' deveria navegar para a página de pesquisa");
    }
}
