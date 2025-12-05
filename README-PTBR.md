# 📘 **Baseline-SheetReader: Estudo de Caso de Refatoração com Padrões de Projeto**

> *Estudo de Caso Acadêmico: Avaliando o Impacto dos Padrões de Projeto na Qualidade de Software*

---

## 🧩 **1. Visão Geral do Projeto**

O **SheetReader** foi desenvolvido como um estudo de caso para avaliar como a aplicação de **padrões de projeto orientados a objetos** afeta a **qualidade, manutenibilidade e escalabilidade** de sistemas Java.

O projeto implementa duas versões distintas da mesma funcionalidade — leitura e importação de planilhas Excel:

* **Versão Original:** lógica procedural, “God Classes” monolíticas e alto acoplamento. (Contida em `importable.old`)
* **Versão Refatorada:** reestruturada utilizando **padrões de projeto** (Template Method, Factory Method, Strategy), com foco em **modularidade e extensibilidade**.

---

## 🧱 **2. Arquitetura do Sistema**

Esta seção contrasta visualmente as arquiteturas "antes" e "depois", que são o núcleo deste estudo.

### 🔹 **Versão Original (A Arquitetura "God Class")**

A versão original (`importable.old`) é um exemplo clássico de design procedural monolítico, resultando em uma classificação de **Manutenibilidade Crítica 'E'** no SonarQube.

* **Classes Monolíticas:** `OldProductImporter` continha toda a lógica: E/S de arquivos, parsing de planilhas, validação de dados e criação de modelos.
* **Alto Acoplamento e Baixa Coesão:** Levou a uma extrema rigidez (WMC=72.5) e fragilidade.
* **Sem Extensibilidade:** Adicionar um novo importador (ex: "Endereço") exigiria duplicar centenas de linhas de código.

#### Diagrama de Classes (Alto Acoplamento)

Este diagrama mostra a assinatura do "Método Deus", provando o alto acoplamento. O `Client` precisa conhecer detalhes internos de implementação (as letras das colunas), criando um design frágil.

![Arquitetura Original "God Class"](src/main/resources/images/diagrama_god_class.png)

#### Diagrama de Sequência (Baixa Coesão)

Este diagrama prova a baixa coesão funcional. A classe `OldCustomerImporter` faz todo o trabalho internamente (parsear, mapear, criar) sem delegar nenhuma responsabilidade.

![Sequência Original "God Class"](src/main/resources/images/sequencia_god_class.png)

---

### 🔹 **Versão Refatorada (A Arquitetura de Padrões de Projeto)**

A versão refatorada aplica múltiplos padrões de projeto para distribuir a complexidade, seguir os princípios SOLID e alcançar uma classificação de **Manutenibilidade Excelente 'A'**.

* **Padrões de Projeto Implementados:**
    * **Template Method** → define o esqueleto genérico do fluxo de importação (`GenericImportMapper`).
    * **Factory Method** → `ModelConfigFactory` instancia dinamicamente a lógica de importação correta.
    * **Strategy** → `CustomerImportationMapper` e `ProductImportationMapper` são "estratégias" concretas para mapeamento de dados.
* **Princípios SOLID:**
    * **SRP (Responsabilidade Única):** Cada classe tem um trabalho (uma Factory cria, um Service orquestra, um Mapper mapeia).
    * **OCP (Aberto/Fechado):** O sistema está **aberto** para adicionar novos importadores (ex: `InvoiceImportationMapper`), mas **fechado** para modificação (não é necessário alterar o `ImportSheetService`).

#### Diagrama de Arquitetura (Factory, Strategy, Template)

Este diagrama mostra como os padrões colaboram para criar um sistema desacoplado e extensível.

![Arquitetura Refatorada com Padrões de Projeto](src/main/resources/images/diagrama_arquitetura.png)

#### Diagrama de Sequência (Baixo Acoplamento na Execução)

Este diagrama prova o baixo acoplamento em tempo de execução. O `Client` (Main) está completamente isolado das implementações concretas de `Mapper`, interagindo apenas com abstrações fornecidas pelas factories.

![Diagrama de Sequência da Execução Refatorada](src/main/resources/images/diagrama_sequencia.png)

📂 **Pacotes Principais**

---

## ⚙️ **3. Ferramentas Utilizadas**

| Ferramenta | Propósito |
| :--- | :--- |
| **CKJM Extended** | Métricas em nível de classe (WMC, CBO, RFC, LCOM, etc.) |
| **SonarQube** | Análise holística de qualidade e manutenibilidade |
| **Maven** | Gerenciamento de dependências e build |
| **Apache POI** | Processamento de planilhas Excel |
| **Java 17** | Linguagem de programação utilizada |

---

## 📊 **4. Resultados e Discussão**

### 4.1. **Análise de Métricas de Nível de Classe (CKJM)**

A análise estática usando **CKJM-Extended** focou em Acoplamento (CBO, RFC), Coesão (LCOM) e Complexidade (WMC).

| Métrica (Foco) | Versão Original | Versão Refatorada | Mudança | Impacto na Qualidade |
| :--- | :--- | :--- | :--- | :--- |
| **CBO (Acoplamento)** | 6.00 | 4.85 | -19.2% | ✅ Acoplamento reduzido |
| **RFC (Resposta da Classe)** | 29.00 | 17.59 | -39.3% | ✅ Menor complexidade de resposta |
| **LCOM (Falta de Coesão)** | 1.00 | 23.85 | +2285% | ⚠️ Requer análise qualitativa |
| **WMC (Complexidade)** | 2.00 | 6.64 | +232% | ⚠️ Aumentou devido à modularização |

📌 **Interpretação:**
A versão refatorada reduziu significativamente o acoplamento, mas aumentou o número de classes — distribuindo a complexidade de forma mais uniforme.
A "Versão Original" continha **God Classes** como `OldProductImporter` (WMC=72.5), que inflacionavam as métricas artificialmente.
A nova arquitetura segue o **Princípio da Responsabilidade Única (SRP)**, resultando em maior manutenibilidade e coesão lógica.

---

### 4.2. **Análise de Qualidade SonarQube**

A análise holística usando **SonarQube** validou as descobertas do CKJM:

| Métrica (SonarQube) | Original (299 LOC) | Refatorada (1.8k LOC) |
| :--- | :--- | :--- |
| **Segurança (Vulnerabilidades)** | A (0 Issues) | A (0 Issues) |
| **Manutenibilidade** | E (Crítica) | A (Excelente) |
| **Dívida Técnica** | 23 min | 77 min |

📌 **Interpretação:**
Apesar de ser **seis vezes maior**, a versão refatorada alcançou a **classificação máxima de manutenibilidade (A)**.
A versão original, embora menor, estava densamente repleta de "Code Smells" e problemas estruturais, recebendo uma **classificação crítica (E)**.

---

### 4.3. **Simulação de Escalabilidade**

Um teste de escalabilidade simulou o crescimento do sistema de **2 para 10 importadores**, estimando o impacto na complexidade e na dívida técnica.

| Métrica (Total do Projeto) | Original (2 imports) | Original (10 imports) | Refatorada (2 imports) | Refatorada (10 imports) |
| :--- | :--- | :--- | :--- | :--- |
| **Complexidade Total (WMC)** | ~153 | ~765 | ~285 | ~365 |
| **Manutenibilidade (Sonar)** | E | E | A | A |
| **Dívida Técnica** | 23 min | ~115 min | 77 min | ~101 min |

📌 **Interpretação:**
A versão procedural **não escala** — a complexidade cresce exponencialmente.
Em contraste, a versão refatorada mantém um **crescimento linear de complexidade**, mantendo a manutenibilidade na **classificação A**.
Mesmo com mais importadores, a dívida técnica total permanece **menor** do que no sistema procedural, provando o **retorno a longo prazo de uma boa arquitetura**.

---

## 🧮 **5. Conclusão**

Este estudo demonstra quantitativamente o impacto positivo dos **padrões de projeto** na estrutura e manutenibilidade do software.

* **CBO (-19%) e RFC (-39%)**: redução significativa no acoplamento e na complexidade de resposta.
* Os aumentos em **WMC e LCOM** são esperados — a complexidade foi **distribuída**, não acumulada.
* **SonarQube** confirma: a manutenibilidade melhorou de **E (Crítica)** para **A (Excelente)**.
* A **simulação de escalabilidade** mostra que a arquitetura refatorada suporta um crescimento sustentável, enquanto o design original se torna inmanutenível.

🔹 **Declaração Final:**

> O investimento inicial em padrões de projeto gera benefícios a longo prazo — resultando em um sistema que é mais **modular, coeso, extensível e manutenível**.

---

## 📘 **6. Definições das Métricas CKJM**

| Métrica | Descrição | Desejável | Interpretação |
| :--- | :--- | :--- | :--- |
| **CBO** | Acoplamento entre Objetos — número de classes dependentes | Baixo | Menos dependências externas |
| **LCOM** | Falta de Coesão nos Métodos — mede o foco da classe | Baixo | Alta coesão e aderência ao SRP |
| **RFC** | Resposta da Classe — número de métodos chamáveis | Baixo | Menor complexidade, classes mais simples |
| **WMC** | Métodos Ponderados por Classe — complexidade total | Baixo | Mais fácil de entender e manter |
| **DIT** | Profundidade da Árvore de Herança | Médio | Bom reuso sem hierarquias profundas |
| **NOC** | Número de Filhos (Children) | Baixo–Médio | Indica reuso através de herança |
| **Ca** | Acoplamento Aferente — classes que dependem desta | Médio | Indica classes centrais ou nucleares |
| **Ce** | Acoplamento Eferente — classes das quais esta depende | Baixo | Indica independência |
| **NPM** | Número de Métodos Públicos | Baixo | Interfaces menores e mais simples |
| **LOC** | Linhas de Código | Baixo | Base de código mais simples e menor |

---

## 🧰 **7. Como Executar**

### 🖥️ **Requisitos**

* Java 17+
* Maven 3.9+
* Apache POI (incluído via Maven)

### ▶️ **Comandos**

```bash
# Clonar repositório
git clone [https://github.com/AfonsoFernando051/Java-Maven-SheetReader.git](https://github.com/AfonsoFernando051/Java-Maven-SheetReader.git)
cd Java-Maven-SheetReader

# Build (Construir o projeto)
mvn clean package

# Executar versão refatorada
java -cp target/SheetReader-1.0.jar importable.run.Main

# Executar versão original (sem padrões de projeto)
java -cp target/SheetReader-1.0.jar importable.old.OldProductImporter

---

## 📈 **8. Tendências das Métricas CKJM**

| Metric   | Trend                           |
| -------- | ------------------------------- |
| **CBO**  | 🔻 Acoplamento reduzido            |
| **RFC**  | 🔻 Complexidade de resposta reduzida  |
| **LCOM** | 🔺 Maior devido à modularização |
| **WMC**  | 🔺 Complexidade distribuída       |

## 👨‍💻 **9. Autor & Referências**

**Fernando Afonso de Souza Dias** & **Esdras Altivo Batista Corrêa**
*Faculdade de Ciências Empresariais - Universidade FUMEC*

Este projeto foi desenvolvido como parte de um estudo de caso acadêmico para um **Trabalho de Conclusão de Curso (TCC)** referente a Engenharia de Software e Padrões de Projeto.

### 📄 **Artigo Acadêmico**
* **Título:** *Impacto dos Padrões de Projeto na Escalabilidade: Uma Análise Quantitativa de Custo-Benefício e Manutenibilidade em Java*
* **Link:** [📄 **Ler o Artigo Completo (PDF)**](./docs/Impacto_dos_Padrões_de_Projeto_na_Escalabilidade__Uma_Análise_Quantitativa_de_Custo_Benefício_e_Manutenibilidade_em_Java.pdf)
    * *Nota: A análise quantitativa completa e a metodologia descrita neste README estão disponíveis no artigo acima.*
* **Resumo:** Investiga a aplicação prática de padrões de projeto (Factory, Strategy, Template Method) em sistemas de processamento de dados, demonstrando ganhos quantitativos em coesão, acoplamento e escalabilidade.

### 🔗 **Versões do Projeto**
* ✨ **Projeto Atual (Refatorado):** [**Java-Maven-SheetReader**](https://github.com/AfonsoFernando051/Java-Maven-SheetReader)
    * *A versão recomendada. Arquitetura limpa, modular e extensível usando Padrões de Projeto.*

* 🕸️ **Projeto Legado (Baseline):** [**Legacy-SheetReader (God Class)**](https://github.com/AfonsoFernando051/Baseline-SheetReader)
    * *A versão do grupo de controle. Código monolítico, procedural e altamente acoplado usado para comparação no estudo.*

---
📎 **Perfil GitHub:** [@AfonsoFernando051](https://github.com/AfonsoFernando051)