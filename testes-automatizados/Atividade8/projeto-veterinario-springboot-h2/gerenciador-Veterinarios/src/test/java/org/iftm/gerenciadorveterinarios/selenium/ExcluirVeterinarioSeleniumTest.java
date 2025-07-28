package org.iftm.gerenciadorveterinarios.selenium;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Alert;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes automatizados para a exclusão de veterinários usando Selenium
 * WebDriver
 * 
 * Cenários testados:
 * 1. Excluir veterinário existente
 * 2. Verificar remoção da lista
 * 3. Verificar botões de exclusão na tabela
 * 4. Testar funcionalidade do botão de exclusão
 */
class ExcluirVeterinarioSeleniumTest extends BaseSeleniumTest {

    @Test
    @DisplayName("Cenário 1: Excluir veterinário existente")
    void testExcluirVeterinarioExistente() {
        // DEFINIÇÃO DO CENÁRIO
        // Primeiro, vamos cadastrar um veterinário para depois excluí-lo
        String nome = "Dr. Para Excluir";
        String email = "excluir@teste.com";
        String especialidade = "Teste";
        String salario = "1000.00";

        // CHAMADA DA AÇÃO
        // 1. Navegar para a página inicial
        navegarParaHome();

        // 2. Cadastrar um veterinário primeiro
        WebElement botaoAdicionar = driver.findElement(By.linkText("Adicionar"));
        botaoAdicionar.click();

        // Preencher formulário
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

        // 3. Voltar para a página inicial e verificar se o veterinário foi cadastrado
        navegarParaHome();
        WebElement tabela = driver.findElement(By.className("table"));
        String textoTabelaAntes = tabela.getText();
        assertTrue(textoTabelaAntes.contains(nome),
                "Veterinário deveria estar na tabela antes da exclusão");

        // 4. Encontrar e clicar no botão de excluir do veterinário recém-cadastrado
        // Buscar pela linha que contém o nome do veterinário e clicar no botão de
        // excluir
        WebElement botaoExcluir = driver.findElement(
                By.xpath("//tr[contains(td, '" + nome + "')]//a[contains(@class, 'btn-danger')]"));
        botaoExcluir.click();

        // VERIFICAÇÃO DA RESPOSTA
        // 1. Verificar se foi redirecionado para a página inicial
        assertTrue(driver.getCurrentUrl().contains("/home"),
                "Deveria ter sido redirecionado para a página inicial após exclusão");

        // 2. Verificar se o veterinário foi removido da tabela
        WebElement tabelaAposExclusao = driver.findElement(By.className("table"));
        String textoTabelaDepois = tabelaAposExclusao.getText();
        assertFalse(textoTabelaDepois.contains(nome),
                "Veterinário não deveria mais aparecer na tabela após exclusão");
        assertFalse(textoTabelaDepois.contains(email),
                "Email do veterinário não deveria mais aparecer na tabela após exclusão");
    }

    @Test
    @DisplayName("Cenário 2: Verificar presença de botões de exclusão na tabela")
    void testVerificarBotoesExclusaoNaTabela() {
        // DEFINIÇÃO DO CENÁRIO
        // Verificar se todos os veterinários na tabela possuem botão de exclusão

        // CHAMADA DA AÇÃO
        // 1. Navegar para a página inicial
        navegarParaHome();

        // VERIFICAÇÃO DA RESPOSTA
        // 1. Verificar se existe uma tabela
        WebElement tabela = driver.findElement(By.className("table"));
        assertNotNull(tabela, "Deveria existir uma tabela de veterinários");

        // 2. Verificar se existem botões de exclusão (ícone de lixeira)
        try {
            WebElement botaoExcluir = driver.findElement(
                    By.xpath("//a[contains(@class, 'btn-danger')]//i[contains(@class, 'fa-trash')]"));
            assertNotNull(botaoExcluir, "Deveria existir pelo menos um botão de exclusão com ícone de lixeira");
        } catch (Exception e) {
            // Se não há veterinários cadastrados, verificar se pelo menos a estrutura da
            // tabela está correta
            assertTrue(tabela.getText().contains("Nome"),
                    "Tabela deveria ter cabeçalho 'Nome' mesmo sem dados");
        }

        // 3. Verificar se os botões de exclusão têm a classe CSS correta
        try {
            WebElement botaoExcluir = driver.findElement(By.xpath("//a[contains(@class, 'btn-danger')]"));
            assertTrue(botaoExcluir.getAttribute("class").contains("btn-danger"),
                    "Botão de exclusão deveria ter classe 'btn-danger'");
        } catch (Exception e) {
            // Caso não existam veterinários cadastrados, o teste ainda passa
            System.out.println("Nenhum veterinário encontrado para testar botões de exclusão");
        }
    }

    @Test
    @DisplayName("Cenário 3: Verificar estrutura dos botões de ação na tabela")
    void testVerificarEstruturaBotoesAcao() {
        // DEFINIÇÃO DO CENÁRIO
        // Verificar se a tabela tem a estrutura correta para os botões de ação

        // CHAMADA DA AÇÃO
        // 1. Navegar para a página inicial
        navegarParaHome();

        // VERIFICAÇÃO DA RESPOSTA
        // 1. Verificar se existe uma tabela
        WebElement tabela = driver.findElement(By.className("table"));
        assertNotNull(tabela, "Deveria existir uma tabela de veterinários");

        // 2. Verificar se a tabela tem as colunas corretas
        String textoTabela = tabela.getText();
        assertTrue(textoTabela.contains("Nome"), "Tabela deveria ter coluna 'Nome'");
        assertTrue(textoTabela.contains("Especialidade"), "Tabela deveria ter coluna 'Especialidade'");
        assertTrue(textoTabela.contains("Email"), "Tabela deveria ter coluna 'Email'");
        assertTrue(textoTabela.contains("Salario"), "Tabela deveria ter coluna 'Salario'");

        // 3. Verificar se existem botões de ação (editar e excluir)
        try {
            // Verificar botão de editar
            WebElement botaoEditar = driver.findElement(By.xpath("//a[contains(@class, 'btn-warning')]"));
            assertTrue(botaoEditar.getAttribute("class").contains("btn-warning"),
                    "Deveria existir botão de edição com classe 'btn-warning'");

            // Verificar botão de excluir
            WebElement botaoExcluir = driver.findElement(By.xpath("//a[contains(@class, 'btn-danger')]"));
            assertTrue(botaoExcluir.getAttribute("class").contains("btn-danger"),
                    "Deveria existir botão de exclusão com classe 'btn-danger'");

        } catch (Exception e) {
            System.out.println("Nenhum veterinário encontrado para verificar botões de ação");
        }
    }

    @Test
    @DisplayName("Cenário 4: Verificar links dos botões de exclusão")
    void testVerificarLinksExclusao() {
        // DEFINIÇÃO DO CENÁRIO
        // Verificar se os botões de exclusão têm os links corretos

        // CHAMADA DA AÇÃO
        // 1. Navegar para a página inicial
        navegarParaHome();

        // VERIFICAÇÃO DA RESPOSTA
        try {
            // 1. Verificar se existem botões de exclusão
            WebElement botaoExcluir = driver.findElement(By.xpath("//a[contains(@class, 'btn-danger')]"));
            assertNotNull(botaoExcluir, "Deveria existir pelo menos um botão de exclusão");

            // 2. Verificar se o link do botão segue o padrão esperado
            String href = botaoExcluir.getAttribute("href");
            assertTrue(href.contains("/delete/"),
                    "Link do botão de exclusão deveria conter '/delete/'");

            // 3. Verificar se o link termina com um número (ID do veterinário)
            assertTrue(href.matches(".*delete/\\d+$"),
                    "Link deveria terminar com '/delete/' seguido de um número (ID)");

        } catch (Exception e) {
            System.out.println("Nenhum veterinário encontrado para verificar links de exclusão");
        }
    }

    @Test
    @DisplayName("Cenário 5: Verificar comportamento após exclusão de todos os veterinários")
    void testTabelaVaziaAposExclusoes() {
        // DEFINIÇÃO DO CENÁRIO
        // Verificar como a aplicação se comporta quando não há veterinários cadastrados

        // CHAMADA DA AÇÃO
        // 1. Navegar para a página inicial
        navegarParaHome();

        // 2. Excluir todos os veterinários que existem (se houver)
        boolean continuarExcluindo = true;
        while (continuarExcluindo) {
            try {
                WebElement botaoExcluir = driver.findElement(By.xpath("//a[contains(@class, 'btn-danger')]"));
                botaoExcluir.click();
                // Aguardar um pouco para a página recarregar
                Thread.sleep(1000);
            } catch (Exception e) {
                // Não há mais botões de exclusão, sair do loop
                continuarExcluindo = false;
            }
        }

        // VERIFICAÇÃO DA RESPOSTA
        // 1. Verificar se ainda existe a tabela
        WebElement tabela = driver.findElement(By.className("table"));
        assertNotNull(tabela, "Tabela deveria continuar existindo mesmo sem dados");

        // 2. Verificar se a tabela ainda tem os cabeçalhos
        String textoTabela = tabela.getText();
        assertTrue(textoTabela.contains("Nome"), "Cabeçalho 'Nome' deveria continuar visível");
        assertTrue(textoTabela.contains("Especialidade"), "Cabeçalho 'Especialidade' deveria continuar visível");

        // 3. Verificar se não existem mais botões de ação
        try {
            WebElement botaoExcluir = driver.findElement(By.xpath("//a[contains(@class, 'btn-danger')]"));
            fail("Não deveria existir botão de exclusão quando não há veterinários");
        } catch (Exception e) {
            // Esperado - não deve existir botão quando não há dados
            assertTrue(true, "Correto - não existem botões de exclusão quando não há veterinários");
        }

        // 4. Verificar se os botões de adicionar e consultar ainda funcionam
        WebElement botaoAdicionar = driver.findElement(By.linkText("Adicionar"));
        assertNotNull(botaoAdicionar, "Botão 'Adicionar' deveria continuar disponível");

        WebElement botaoConsultar = driver.findElement(By.linkText("Consultar"));
        assertNotNull(botaoConsultar, "Botão 'Consultar' deveria continuar disponível");
    }
}
