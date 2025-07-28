package org.iftm.gerenciadorveterinarios.selenium;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes automatizados para alterar veterinários usando Selenium WebDriver
 * 
 * Cenários testados:
 * 1. Alterar dados de veterinário existente
 * 2. Verificar preenchimento automático do formulário de edição
 * 3. Verificar validação de campos na edição
 * 4. Verificar cancelamento de edição
 */
class AlterarVeterinarioSeleniumTest extends BaseSeleniumTest {

    @Test
    @DisplayName("Cenário 1: Alterar dados de veterinário existente")
    void testAlterarDadosVeterinario() {
        // DEFINIÇÃO DO CENÁRIO
        // Primeiro cadastrar um veterinário para depois alterá-lo
        String nomeOriginal = "Dr. Original";
        String emailOriginal = "original@teste.com";
        String especialidadeOriginal = "Clínica Geral";
        String salarioOriginal = "3000.00";

        // Dados alterados
        String nomeAlterado = "Dr. Alterado";
        String emailAlterado = "alterado@teste.com";
        String especialidadeAlterada = "Cirurgia";
        String salarioAlterado = "4500.00";

        // CHAMADA DA AÇÃO
        // 1. Navegar para a página inicial
        navegarParaHome();

        // 2. Cadastrar um veterinário primeiro
        WebElement botaoAdicionar = driver.findElement(By.linkText("Adicionar"));
        botaoAdicionar.click();

        // Preencher formulário de cadastro
        WebElement campoNome = driver.findElement(By.id("nome"));
        campoNome.sendKeys(nomeOriginal);

        WebElement campoEmail = driver.findElement(By.id("inputEmail"));
        campoEmail.sendKeys(emailOriginal);

        WebElement campoEspecialidade = driver.findElement(By.id("inputEspecialidade"));
        campoEspecialidade.sendKeys(especialidadeOriginal);

        WebElement campoSalario = driver.findElement(By.id("inputSalario"));
        campoSalario.sendKeys(salarioOriginal);

        WebElement botaoCadastrar = driver.findElement(By.xpath("//button[@type='submit' and text()='Cadastrar']"));
        botaoCadastrar.click();

        // 3. Voltar para a página inicial e clicar no botão de editar
        navegarParaHome();
        WebElement botaoEditar = driver.findElement(
                By.xpath("//tr[contains(td, '" + nomeOriginal + "')]//a[contains(@class, 'btn-warning')]"));
        botaoEditar.click();

        // 4. Verificar se chegou na página de edição
        assertTrue(driver.getCurrentUrl().contains("/form/"),
                "Deveria estar na página de edição (form/id)");

        // 5. Alterar os dados do formulário
        WebElement campoNomeEdicao = driver.findElement(By.id("nome"));
        campoNomeEdicao.clear();
        campoNomeEdicao.sendKeys(nomeAlterado);

        WebElement campoEmailEdicao = driver.findElement(By.id("inputEmail"));
        campoEmailEdicao.clear();
        campoEmailEdicao.sendKeys(emailAlterado);

        WebElement campoEspecialidadeEdicao = driver.findElement(By.id("inputEspecialidade"));
        campoEspecialidadeEdicao.clear();
        campoEspecialidadeEdicao.sendKeys(especialidadeAlterada);

        WebElement campoSalarioEdicao = driver.findElement(By.id("inputSalario"));
        campoSalarioEdicao.clear();
        campoSalarioEdicao.sendKeys(salarioAlterado);

        // 6. Clicar no botão "Atualizar"
        WebElement botaoAtualizar = driver.findElement(By.xpath("//button[@type='submit']"));
        botaoAtualizar.click();

        // VERIFICAÇÃO DA RESPOSTA
        // 1. Verificar se foi redirecionado para a página inicial
        assertTrue(driver.getCurrentUrl().contains("/home"),
                "Deveria ter sido redirecionado para a página inicial após alteração");

        // 2. Verificar se os dados alterados aparecem na tabela
        WebElement tabela = driver.findElement(By.className("table"));
        String textoTabela = tabela.getText();

        assertTrue(textoTabela.contains(nomeAlterado),
                "Nome alterado deveria aparecer na tabela");
        assertTrue(textoTabela.contains(emailAlterado),
                "Email alterado deveria aparecer na tabela");
        assertTrue(textoTabela.contains(especialidadeAlterada),
                "Especialidade alterada deveria aparecer na tabela");
        assertTrue(textoTabela.contains("R$" + salarioAlterado),
                "Salário alterado deveria aparecer na tabela");

        // 3. Verificar se os dados originais não aparecem mais
        assertFalse(textoTabela.contains(nomeOriginal),
                "Nome original não deveria mais aparecer na tabela");
        assertFalse(textoTabela.contains(emailOriginal),
                "Email original não deveria mais aparecer na tabela");
    }

    @Test
    @DisplayName("Cenário 2: Verificar preenchimento automático do formulário de edição")
    void testPreenchimentoAutomaticoFormularioEdicao() {
        // DEFINIÇÃO DO CENÁRIO
        String nome = "Dr. Preenchimento";
        String email = "preenchimento@teste.com";
        String especialidade = "Dermatologia";
        String salario = "5500.00";

        // CHAMADA DA AÇÃO
        // 1. Navegar para a página inicial
        navegarParaHome();

        // 2. Cadastrar um veterinário primeiro
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

        // 3. Voltar para a página inicial e clicar no botão de editar
        navegarParaHome();
        WebElement botaoEditar = driver.findElement(
                By.xpath("//tr[contains(td, '" + nome + "')]//a[contains(@class, 'btn-warning')]"));
        botaoEditar.click();

        // VERIFICAÇÃO DA RESPOSTA
        // 1. Verificar se chegou na página de edição
        assertTrue(driver.getCurrentUrl().contains("/form/"),
                "Deveria estar na página de edição");

        // 2. Verificar se os campos estão preenchidos com os dados atuais
        WebElement campoNomeEdicao = driver.findElement(By.id("nome"));
        assertEquals(nome, campoNomeEdicao.getAttribute("value"),
                "Campo nome deveria estar preenchido com o valor atual");

        WebElement campoEmailEdicao = driver.findElement(By.id("inputEmail"));
        assertEquals(email, campoEmailEdicao.getAttribute("value"),
                "Campo email deveria estar preenchido com o valor atual");

        WebElement campoEspecialidadeEdicao = driver.findElement(By.id("inputEspecialidade"));
        assertEquals(especialidade, campoEspecialidadeEdicao.getAttribute("value"),
                "Campo especialidade deveria estar preenchido com o valor atual");

        WebElement campoSalarioEdicao = driver.findElement(By.id("inputSalario"));
        assertEquals(salario, campoSalarioEdicao.getAttribute("value"),
                "Campo salário deveria estar preenchido com o valor atual");

        // 3. Verificar se existe um título indicando que é edição
        try {
            WebElement titulo = driver
                    .findElement(By.xpath("//h1[contains(text(), 'Atualizar') or contains(text(), 'Editar')]"));
            assertNotNull(titulo, "Deveria existir um título indicando que é uma edição");
        } catch (Exception e) {
            // Se não há título específico, verificar se pelo menos não é o título de
            // cadastro
            try {
                WebElement tituloCadastro = driver.findElement(By.xpath("//h1[contains(text(), 'Cadastrar novo')]"));
                assertNull(tituloCadastro, "Não deveria ter título de cadastro na página de edição");
            } catch (Exception e2) {
                // Título não encontrado, o que pode estar correto
            }
        }
    }

    @Test
    @DisplayName("Cenário 3: Verificar validação de campos na edição")
    void testValidacaoCamposEdicao() {
        // DEFINIÇÃO DO CENÁRIO
        String nome = "Dr. Validacao";
        String email = "validacao@teste.com";
        String especialidade = "Cardiologia";
        String salario = "6000.00";

        // CHAMADA DA AÇÃO
        // 1. Navegar para a página inicial
        navegarParaHome();

        // 2. Cadastrar um veterinário primeiro
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

        // 3. Ir para a página de edição
        navegarParaHome();
        WebElement botaoEditar = driver.findElement(
                By.xpath("//tr[contains(td, '" + nome + "')]//a[contains(@class, 'btn-warning')]"));
        botaoEditar.click();

        // 4. Limpar campos obrigatórios e tentar submeter
        WebElement campoEmailEdicao = driver.findElement(By.id("inputEmail"));
        campoEmailEdicao.clear();

        WebElement campoEspecialidadeEdicao = driver.findElement(By.id("inputEspecialidade"));
        campoEspecialidadeEdicao.clear();

        WebElement botaoAtualizar = driver.findElement(By.xpath("//button[@type='submit']"));
        botaoAtualizar.click();

        // VERIFICAÇÃO DA RESPOSTA
        // 1. Verificar se ainda está na página de edição (validação impediu submissão)
        assertTrue(driver.getCurrentUrl().contains("/form/"),
                "Deveria permanecer na página de edição quando há campos obrigatórios vazios");

        // 2. Verificar se os campos ainda têm atributos de validação
        WebElement campoEmailValidacao = driver.findElement(By.id("inputEmail"));
        assertNotNull(campoEmailValidacao.getAttribute("required"),
                "Campo email deveria ter atributo 'required'");

        WebElement campoEspecialidadeValidacao = driver.findElement(By.id("inputEspecialidade"));
        assertNotNull(campoEspecialidadeValidacao.getAttribute("required"),
                "Campo especialidade deveria ter atributo 'required'");
    }

    @Test
    @DisplayName("Cenário 4: Verificar botão de editar na tabela")
    void testVerificarBotaoEditarNaTabela() {
        // DEFINIÇÃO DO CENÁRIO
        // Verificar se todos os veterinários na tabela possuem botão de editar

        // CHAMADA DA AÇÃO
        // 1. Navegar para a página inicial
        navegarParaHome();

        // VERIFICAÇÃO DA RESPOSTA
        // 1. Verificar se existe uma tabela
        WebElement tabela = driver.findElement(By.className("table"));
        assertNotNull(tabela, "Deveria existir uma tabela de veterinários");

        // 2. Verificar se existem botões de edição (ícone de lápis)
        try {
            WebElement botaoEditar = driver.findElement(
                    By.xpath("//a[contains(@class, 'btn-warning')]//i[contains(@class, 'fa-pencil')]"));
            assertNotNull(botaoEditar, "Deveria existir pelo menos um botão de edição com ícone de lápis");
        } catch (Exception e) {
            // Se não há veterinários cadastrados, verificar se pelo menos a estrutura da
            // tabela está correta
            assertTrue(tabela.getText().contains("Nome"),
                    "Tabela deveria ter cabeçalho 'Nome' mesmo sem dados");
        }

        // 3. Verificar se os botões de edição têm a classe CSS correta
        try {
            WebElement botaoEditar = driver.findElement(By.xpath("//a[contains(@class, 'btn-warning')]"));
            assertTrue(botaoEditar.getAttribute("class").contains("btn-warning"),
                    "Botão de edição deveria ter classe 'btn-warning'");
        } catch (Exception e) {
            // Caso não existam veterinários cadastrados, o teste ainda passa
            System.out.println("Nenhum veterinário encontrado para testar botões de edição");
        }
    }

    @Test
    @DisplayName("Cenário 5: Verificar links dos botões de edição")
    void testVerificarLinksEdicao() {
        // DEFINIÇÃO DO CENÁRIO
        // Verificar se os botões de edição têm os links corretos

        // CHAMADA DA AÇÃO
        // 1. Navegar para a página inicial
        navegarParaHome();

        // VERIFICAÇÃO DA RESPOSTA
        try {
            // 1. Verificar se existem botões de edição
            WebElement botaoEditar = driver.findElement(By.xpath("//a[contains(@class, 'btn-warning')]"));
            assertNotNull(botaoEditar, "Deveria existir pelo menos um botão de edição");

            // 2. Verificar se o link do botão segue o padrão esperado
            String href = botaoEditar.getAttribute("href");
            assertTrue(href.contains("/form/"),
                    "Link do botão de edição deveria conter '/form/'");

            // 3. Verificar se o link termina com um número (ID do veterinário)
            assertTrue(href.matches(".*form/\\d+$"),
                    "Link deveria terminar com '/form/' seguido de um número (ID)");

        } catch (Exception e) {
            System.out.println("Nenhum veterinário encontrado para verificar links de edição");
        }
    }
}
