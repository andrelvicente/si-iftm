package org.iftm.gerenciadorveterinarios.selenium;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes automatizados para o cadastro de veterinários usando Selenium
 * WebDriver
 * 
 * Cenários testados:
 * 1. Cadastrar veterinário com dados válidos
 * 2. Verificar campos obrigatórios
 * 3. Validar formato de email
 */
public class CadastrarVeterinarioSeleniumTest extends BaseSeleniumTest {

    @Test
    @DisplayName("Cenário 1: Cadastrar veterinário com dados válidos")
    public void testCadastrarVeterinarioComDadosValidos() {
        // DEFINIÇÃO DO CENÁRIO
        String nome = "Dr. João Silva";
        String email = "joao.silva@veterinaria.com";
        String especialidade = "Cirurgia Veterinária";
        BigDecimal salario = new BigDecimal("5000.00");

        // CHAMADA DA AÇÃO
        // 1. Navegar para a página inicial
        navegarParaHome();

        // 2. Clicar no botão "Adicionar"
        WebElement botaoAdicionar = driver.findElement(By.linkText("Adicionar"));
        botaoAdicionar.click();

        // 3. Aguardar carregamento da página de cadastro
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("nome")));

        // 4. Preencher os campos do formulário
        WebElement campoNome = driver.findElement(By.id("nome"));
        campoNome.clear();
        campoNome.sendKeys(nome);

        WebElement campoEmail = driver.findElement(By.id("inputEmail"));
        campoEmail.clear();
        campoEmail.sendKeys(email);

        WebElement campoEspecialidade = driver.findElement(By.id("inputEspecialidade"));
        campoEspecialidade.clear();
        campoEspecialidade.sendKeys(especialidade);

        WebElement campoSalario = driver.findElement(By.id("inputSalario"));
        campoSalario.clear();
        campoSalario.sendKeys(salario.toString());

        // 5. Clicar no botão "Cadastrar"
        WebElement botaoCadastrar = driver.findElement(By.xpath("//button[@type='submit' and text()='Cadastrar']"));
        botaoCadastrar.click();

        // VERIFICAÇÃO DA RESPOSTA
        // 1. Verificar se foi redirecionado para a página inicial
        wait.until(ExpectedConditions.urlContains("/home"));
        assertTrue(driver.getCurrentUrl().contains("/home"),
                "Deveria ter sido redirecionado para a página inicial após o cadastro");

        // 2. Verificar se o veterinário foi adicionado na tabela
        WebElement tabelaVeterinarios = driver.findElement(By.className("table"));
        String textoTabela = tabelaVeterinarios.getText();

        assertTrue(textoTabela.contains(nome),
                "O nome do veterinário deveria aparecer na tabela");
        assertTrue(textoTabela.contains(email),
                "O email do veterinário deveria aparecer na tabela");
        assertTrue(textoTabela.contains(especialidade),
                "A especialidade do veterinário deveria aparecer na tabela");
        assertTrue(textoTabela.contains("R$" + salario),
                "O salário do veterinário deveria aparecer na tabela");
    }

    @Test
    @DisplayName("Cenário 2: Verificar validação de campos obrigatórios")
    public void testValidacaoCamposObrigatorios() {
        // DEFINIÇÃO DO CENÁRIO
        // Tentar cadastrar veterinário sem preencher campos obrigatórios

        // CHAMADA DA AÇÃO
        // 1. Navegar para a página inicial
        navegarParaHome();

        // 2. Clicar no botão "Adicionar"
        WebElement botaoAdicionar = driver.findElement(By.linkText("Adicionar"));
        botaoAdicionar.click();

        // 3. Aguardar carregamento da página de cadastro
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("nome")));

        // 4. Preencher apenas o nome (deixar outros campos vazios)
        WebElement campoNome = driver.findElement(By.id("nome"));
        campoNome.clear();
        campoNome.sendKeys("Dr. Teste");

        // 5. Tentar submeter o formulário
        WebElement botaoCadastrar = driver.findElement(By.xpath("//button[@type='submit' and text()='Cadastrar']"));
        botaoCadastrar.click();

        // VERIFICAÇÃO DA RESPOSTA
        // 1. Verificar se ainda está na página de cadastro (não foi submetido)
        assertTrue(driver.getCurrentUrl().contains("/form") || driver.getCurrentUrl().contains("/add"),
                "Deveria permanecer na página de cadastro quando há campos obrigatórios vazios");

        // 2. Verificar se o campo email tem validação HTML5
        WebElement campoEmail = driver.findElement(By.id("inputEmail"));
        assertTrue(campoEmail.getAttribute("required") != null,
                "Campo email deveria ter atributo 'required'");

        // 3. Verificar se o campo especialidade tem validação HTML5
        WebElement campoEspecialidade = driver.findElement(By.id("inputEspecialidade"));
        assertTrue(campoEspecialidade.getAttribute("required") != null,
                "Campo especialidade deveria ter atributo 'required'");

        // 4. Verificar se o campo salário tem validação HTML5
        WebElement campoSalario = driver.findElement(By.id("inputSalario"));
        assertTrue(campoSalario.getAttribute("required") != null,
                "Campo salário deveria ter atributo 'required'");
    }

    @Test
    @DisplayName("Cenário 3: Verificar validação de formato de email")
    public void testValidacaoFormatoEmail() {
        // DEFINIÇÃO DO CENÁRIO
        String nome = "Dr. Maria Santos";
        String emailInvalido = "email-invalido";
        String especialidade = "Clínica Geral";
        String salario = "4000.00";

        // CHAMADA DA AÇÃO
        // 1. Navegar para a página inicial
        navegarParaHome();

        // 2. Clicar no botão "Adicionar"
        WebElement botaoAdicionar = driver.findElement(By.linkText("Adicionar"));
        botaoAdicionar.click();

        // 3. Aguardar carregamento da página de cadastro
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("nome")));

        // 4. Preencher os campos com email inválido
        WebElement campoNome = driver.findElement(By.id("nome"));
        campoNome.clear();
        campoNome.sendKeys(nome);

        WebElement campoEmail = driver.findElement(By.id("inputEmail"));
        campoEmail.clear();
        campoEmail.sendKeys(emailInvalido);

        WebElement campoEspecialidade = driver.findElement(By.id("inputEspecialidade"));
        campoEspecialidade.clear();
        campoEspecialidade.sendKeys(especialidade);

        WebElement campoSalario = driver.findElement(By.id("inputSalario"));
        campoSalario.clear();
        campoSalario.sendKeys(salario);

        // 5. Tentar submeter o formulário
        WebElement botaoCadastrar = driver.findElement(By.xpath("//button[@type='submit' and text()='Cadastrar']"));
        botaoCadastrar.click();

        // VERIFICAÇÃO DA RESPOSTA
        // 1. Verificar se ainda está na página de cadastro (validação HTML5 impediu
        // submissão)
        assertTrue(driver.getCurrentUrl().contains("/form") || driver.getCurrentUrl().contains("/add"),
                "Deveria permanecer na página de cadastro quando email está em formato inválido");

        // 2. Verificar se o campo email tem tipo 'email'
        WebElement campoEmail2 = driver.findElement(By.id("inputEmail"));
        assertEquals("email", campoEmail2.getAttribute("type"),
                "Campo email deveria ter type='email' para validação automática");

        // 3. Verificar se o valor preenchido ainda está no campo
        assertEquals(emailInvalido, campoEmail2.getAttribute("value"),
                "O valor do email deveria ser mantido no campo após tentativa de submissão");
    }

    @Test
    @DisplayName("Cenário 4: Verificar preenchimento automático e valores dos campos")
    public void testVerificarValoresCampos() {
        // DEFINIÇÃO DO CENÁRIO
        String nome = "Dr. Carlos Oliveira";
        String email = "carlos.oliveira@petcare.com";
        String especialidade = "Dermatologia Veterinária";
        String salario = "6500.75";

        // CHAMADA DA AÇÃO
        // 1. Navegar para a página inicial
        navegarParaHome();

        // 2. Clicar no botão "Adicionar"
        WebElement botaoAdicionar = driver.findElement(By.linkText("Adicionar"));
        botaoAdicionar.click();

        // 3. Aguardar carregamento da página de cadastro
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("nome")));

        // 4. Verificar valores iniciais dos campos (devem estar vazios)
        WebElement campoNome = driver.findElement(By.id("nome"));
        WebElement campoEmail = driver.findElement(By.id("inputEmail"));
        WebElement campoEspecialidade = driver.findElement(By.id("inputEspecialidade"));
        WebElement campoSalario = driver.findElement(By.id("inputSalario"));

        // VERIFICAÇÃO DA RESPOSTA - Valores iniciais
        assertEquals("", campoNome.getAttribute("value"),
                "Campo nome deveria estar inicialmente vazio");
        assertEquals("", campoEmail.getAttribute("value"),
                "Campo email deveria estar inicialmente vazio");
        assertEquals("", campoEspecialidade.getAttribute("value"),
                "Campo especialidade deveria estar inicialmente vazio");
        assertEquals("", campoSalario.getAttribute("value"),
                "Campo salário deveria estar inicialmente vazio");

        // 5. Preencher os campos
        campoNome.sendKeys(nome);
        campoEmail.sendKeys(email);
        campoEspecialidade.sendKeys(especialidade);
        campoSalario.sendKeys(salario);

        // VERIFICAÇÃO DA RESPOSTA - Valores preenchidos
        assertEquals(nome, campoNome.getAttribute("value"),
                "Campo nome deveria conter o valor preenchido");
        assertEquals(email, campoEmail.getAttribute("value"),
                "Campo email deveria conter o valor preenchido");
        assertEquals(especialidade, campoEspecialidade.getAttribute("value"),
                "Campo especialidade deveria conter o valor preenchido");
        assertEquals(salario, campoSalario.getAttribute("value"),
                "Campo salário deveria conter o valor preenchido");

        // 6. Verificar placeholders dos campos
        assertEquals("Nome Completo", campoNome.getAttribute("placeholder"),
                "Campo nome deveria ter placeholder 'Nome Completo'");
        assertEquals("R$1000.00", campoSalario.getAttribute("placeholder"),
                "Campo salário deveria ter placeholder 'R$1000.00'");
    }
}
