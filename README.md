# Life Sync 

![](https://img.shields.io/badge/javafx-%23FF0000.svg?style=for-the-badge&logo=javafx&logoColor=white) ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) ![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)

[![Generic badge](https://img.shields.io/badge/Versão-2.0.0-<COLOR>.svg)](https://shields.io/) [![GitHub license](https://img.shields.io/github/license/Naereen/StrapDown.js.svg)](https://github.com/Naereen/StrapDown.js/blob/master/LICENSE)

| Life Sync |  @Beforg   |
| -------------  | --- |
| :sparkles: Nome        | **Life Sync**
| :label: Tecnologias | java, javafx, spring, hsqldb, css.
| :date: Data da conclusão       | 24 abr. de 2024
| :back: Download do projeto | [Download do Projeto](https://github.com/Beforg/AtendimentosAPP/releases/download/v3.0.0/atendimentos_setup.exe)
| :balloon: Versão do projeto | Versão 2.0.0 (13 de ago. 2024)

## Descrição

Aplicação JavaFX com Spring, feita através dos conhecimentos adquiridos durante as aulas de Programação I e Banco de Dados I do segundo semestre do curso de Análise e Desenvolvimento de Sistemas, o projeto é um aplicativo de organização com o banco de dados HSQLDB 
integrado localmente. <br>

LifeSync é um aplicativo para gerenciar projetos, tarefas, metas, treinos, estudos e hábitos.

Repositório da primeira versão: [Life Sync v1.0.0](https://github.com/Beforg/LifeSync_v1.0.0)

## Instrução de instalação

Faça o download do aplicativo [aqui](https://github.com/Beforg/AtendimentosAPP/releases/download/v3.0.0/atendimentos_setup.exe) ou clone o repositório:

```pre
git clone https://github.com/Beforg/LifeSync.git
```

## Estrutura do projeto

### Estrutura dos Pacotes do Projeto LifeSync

O projeto LifeSync é organizado em diversos pacotes para manter uma estrutura modular e de fácil manutenção. Abaixo está a descrição dos principais pacotes e suas responsabilidades:

#### `br.com.myegoo.app.myego`
- **Descrição**: Pacote principal do aplicativo, contendo a classe `Launcher` que inicia a aplicação.

#### `br.com.myegoo.app.myego.controller`
- **Descrição**: Contém as classes de controle que lidam com a entrada de dados da interface e direcionam as respostas apropriadas.

#### `br.com.myegoo.app.myego.service`
- **Descrição**: Contém as classes de serviço que implementam a lógica de negócios do aplicativo.

#### `br.com.myegoo.app.myego.repository`
- **Descrição**: Contém as interfaces de repositório que interagem com o banco de dados.


#### `br.com.myegoo.app.myego.model`
- **Descrição**: Contém as classes de modelo que representam as entidades do banco de dados.

#### `br.com.myegoo.app.myego.config`
- **Descrição**: Contém as classes de configuração do Spring Boot e outras configurações necessárias para o funcionamento do aplicativo.

#### `br.com.myegoo.app.myego.util`
- **Descrição**: Contém classes utilitárias e helpers que são usadas em várias partes do aplicativo.

### Exemplo de Estrutura de Diretórios

```plaintext
src/
└── main/
    ├── java/
    │   └── br/
    │       └── com/
    │           └── myegoo/
    │               └── app/
    │                   └── myego/
    │                       ├── controller/
    │                       ├── service/
    │                       ├── repository/
    │                       ├── model/
    │                       ├── config/
    │                       └── util/
    └── resources/
        ├── application.properties
        └── data/
            └── mydb.script
```

Esta estrutura modular facilita a manutenção e a escalabilidade do projeto, permitindo que cada componente tenha uma responsabilidade bem definida.

## Instruções do aplicativo

### Criando perfil

Coloque seus dados para criar seu perfil no aplicativo, o perfil é único.

![](https://github.com/Beforg/assets/blob/main/ls2.0/cadastro.png)

### Tela inicial

Na tela inicial é possível visualizar atividades do dia sendo possível adicionar novas atividades para o dia atual.

![](https://github.com/Beforg/assets/blob/main/ls2.0/inicial.png)

### Tarefas

Na tela de tarefas temos a visualização de uma tabela com tarefas, também um calendário mensal e semanal indicando as tarefas de cada dia.

![](https://github.com/Beforg/assets/blob/main/ls2.0/tarefas.png)

### Estudos

Gerencie suas atividades de estudo, organize seu cronograma e acompanhe suas estatísticas e use a ferramente Pomodoro.

![](https://github.com/Beforg/assets/blob/main/ls2.0/estud.png)

### Treinos

Planeje e registre seus treinos, acompanhe seu desempenho e mantenha-se motivado

![](https://github.com/Beforg/assets/blob/main/ls2.0/treino.png)

### Hábitos

Crie e acompanhe hábitos diários, semanais ou mensais para melhorar sua rotina e alcançar seus objetivos pessoais.

![](https://github.com/Beforg/assets/blob/main/ls2.0/habit.png)

### Projetos 

Crie, edite e acompanhe projetos, dividindo-os em tarefas e etapas para melhor gerenciamento.

![](https://github.com/Beforg/assets/blob/main/ls2.0/projetos.png)

### Metas

Defina metas pessoais e profissionais, monitore seu progresso e alcance seus objetivos

![](https://github.com/Beforg/assets/blob/main/ls2.0/metas.png)

## Considerações finais

Projeto feito por Bruno Forgiarini - Engenharia de Computação.
