package br.com.kelvinsantiago.enums;

import br.com.kelvinsantiago.KeycloakAuthorizationService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static br.com.kelvinsantiago.KeycloakAuthorizationService.Scopes.*;

public enum Resources {
    USUARIO("USUARIO", Map.of(KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()))),
    GRUPO("GRUPO", Map.of(KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()))),
    PERMISSAO("PERMISSAO", Map.of(KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()))),
    LOG("LOG", Map.of(KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()))),
    EMPRESA("EMPRESA", Map.of(KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()))),
    FILIAL("FILIAL", Map.of(KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()))),
    FORNECEDOR("FORNECEDOR", Map.of(KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()))),
    CARGO("CARGO", Map.of(KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()))),
    FUNCIONARIO("FUNCIONARIO", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.SUPERVISOR, List.of(EDITAR, VISUALIZAR, INSERIR),
            KeycloakAuthorizationService.Roles.AT2, List.of(EDITAR, VISUALIZAR, INSERIR))
    ),
    CLIENTE("CLIENTE", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.SUPERVISOR, List.of(VISUALIZAR),
            KeycloakAuthorizationService.Roles.AT2, List.of(VISUALIZAR))
    ),
    EQUIPE("EQUIPE", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.SUPERVISOR, List.of(EDITAR, VISUALIZAR, INSERIR),
            KeycloakAuthorizationService.Roles.AT2, List.of(EDITAR, VISUALIZAR, INSERIR))
    ),
    PROTOCOLO("PROTOCOLO", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.SUPERVISOR, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.AT2, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR))
    ),
    DOMINIO("DOMINIO", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.SUPERVISOR, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.AT2, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR))
    ),
    AVALIACAO("AVALIACAO", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.SUPERVISOR, List.of(EDITAR, VISUALIZAR, INSERIR))
    ),
    FAMILIA("FAMILIA", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.TUTOR, List.of(EDITAR, VISUALIZAR, INSERIR),
            KeycloakAuthorizationService.Roles.SUPERVISOR, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.PROFESSOR, List.of(EDITAR, VISUALIZAR, INSERIR),
            KeycloakAuthorizationService.Roles.PARCEIRO, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.AT, List.of(EDITAR, VISUALIZAR, INSERIR),
            KeycloakAuthorizationService.Roles.AT2, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR))
    ),

    TIPO_AVALIACAO("TIPO_AVALIACAO", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.SUPERVISOR, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.AT2, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR))
    ),
    TIPO_RESPOSTA("TIPO_RESPOSTA", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.SUPERVISOR, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.AT2, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR))
    ),
    TIPO_REFORCO("TIPO_REFORCO", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.SUPERVISOR, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.AT2, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR))
    ),
    PARAMETROS_CLIENTE("PARAMETROS_CLIENTE", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.SUPERVISOR, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.AT2, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR))
    ),
    PROGRAMA("PROGRAMA", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.SUPERVISOR, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.AT2, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR))
    ),
    PROGRAMA_PAIS("PROGRAMA_PAIS", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.TUTOR, List.of(EDITAR, VISUALIZAR, INSERIR),
            KeycloakAuthorizationService.Roles.PROFESSOR, List.of(EDITAR, VISUALIZAR, INSERIR))
    ),
    PROGRAMA_ESCOLA("PROGRAMA_ESCOLA", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.PROFESSOR, List.of(EDITAR, VISUALIZAR, INSERIR))
    ),
    CURRICULUM("CURRICULUM", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.SUPERVISOR, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.AT2, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR))
    ),
    COMPORTAMENTO("COMPORTAMENTO", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()))
    ),
    REFORCADOR("REFORCADOR", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()))
    ),
    INCIDENTE("INCIDENTE", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.TUTOR, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.PROFESSOR, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.SUPERVISOR, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.PARCEIRO, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.AT, List.of(EDITAR, VISUALIZAR, INSERIR),
            KeycloakAuthorizationService.Roles.AT2, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR))
    ),
    REGISTRO("REGISTRO", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.TUTOR, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.PROFESSOR, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.SUPERVISOR, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.PARCEIRO, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.AT, List.of(EDITAR, VISUALIZAR, INSERIR),
            KeycloakAuthorizationService.Roles.AT2, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR))
    ),
    TIPO_REGISTRO("TIPO_REGISTRO", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.PROFESSOR, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.TUTOR, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.SUPERVISOR, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.PARCEIRO, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.AT, List.of(EDITAR, VISUALIZAR, INSERIR),
            KeycloakAuthorizationService.Roles.AT2, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR))
    ),
    LOCAL_REGISTRO("LOCAL_REGISTRO", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()))
    ),
    SESSAO("SESSAO", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.SUPERVISOR, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.AT, List.of(EDITAR, VISUALIZAR, INSERIR),
            KeycloakAuthorizationService.Roles.AT2, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR))
    ),
    SESSAO_PAIS("SESSAO_PAIS", Map.of(
            KeycloakAuthorizationService.Roles.TUTOR, List.of(EDITAR, VISUALIZAR, INSERIR),
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.AT, List.of(EDITAR, VISUALIZAR, INSERIR),
            KeycloakAuthorizationService.Roles.PROFESSOR, List.of(EDITAR, VISUALIZAR, INSERIR))
    ),
    SESSAO_ESCOLA("SESSAO_ESCOLA", Map.of(
            KeycloakAuthorizationService.Roles.PROFESSOR, List.of(EDITAR, VISUALIZAR, INSERIR),
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.AT, List.of(EDITAR, VISUALIZAR, INSERIR))
    ),
    SUPERVISAO("SUPERVISAO", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.SUPERVISOR, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.AT, List.of(VISUALIZAR),
            KeycloakAuthorizationService.Roles.AT2, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR))
    ),
    GRUPO_RELATORIO("GRUPO_RELATORIO", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()))
    ),
    ASSERTIVIDADE_RELATORIO("ASSERTIVIDADE_RELATORIO", Map.of(
            KeycloakAuthorizationService.Roles.SUPERVISOR, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.TUTOR, List.of(EDITAR, VISUALIZAR, INSERIR),
            KeycloakAuthorizationService.Roles.AT, List.of(VISUALIZAR),
            KeycloakAuthorizationService.Roles.AT2, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR))
    ),
    SITUACAO_ALVOS_RELATORIO("SITUACAO_ALVOS_RELATORIO", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.SUPERVISOR, List.of(EDITAR, VISUALIZAR, INSERIR),
            KeycloakAuthorizationService.Roles.AT, List.of(VISUALIZAR),
            KeycloakAuthorizationService.Roles.AT2, List.of(EDITAR, VISUALIZAR, INSERIR))
    ),
    BAIRRO("BAIRRO", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()))
    ),

    CIDADE("CIDADE", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()))
    ),
    ENDERECO("ENDERECO", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()))
    ),
    ESTADO("ESTADO", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()))
    ),
    AGENDA("AGENDA", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.SUPERVISOR, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.AT2, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR))
    ),
    SUPORTE("SUPORTE", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.USUARIO, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.TUTOR, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.SUPERVISOR, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.PROFESSOR, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.PARCEIRO, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.AT2, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR))
    ),
    ESCALA("ESCALA", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.SUPERVISOR, List.of(EDITAR, VISUALIZAR, INSERIR))
    ),
    PRONTUARIO("PRONTUARIO", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.SUPERVISOR, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR),
            KeycloakAuthorizationService.Roles.AT, List.of(EDITAR, VISUALIZAR, INSERIR),
            KeycloakAuthorizationService.Roles.AT2, List.of(EDITAR, VISUALIZAR, INATIVAR, INSERIR))
    ),
    ANAMNESE("ANAMNESE", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()),
            KeycloakAuthorizationService.Roles.SUPERVISOR, List.of(EDITAR, VISUALIZAR, INSERIR))
    ),
    HABILIDADES_BASICAS("HABILIDADES_BASICAS", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()))
    ),
    HABILIDADES_BASICAS_AREAS("HABILIDADES_BASICAS_AREAS", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()))
    ),
    HABILIDADES_BASICAS_PROGRAMAS("HABILIDADES_BASICAS_PROGRAMAS", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()))
    ),
    HABILIDADES_BASICAS_PROGRAMAS_ITENS("HABILIDADES_BASICAS_PROGRAMAS_ITENS", Map.of(
            KeycloakAuthorizationService.Roles.ADMINISTRADOR, Arrays.asList(KeycloakAuthorizationService.Scopes.values()))
    );

    private final String code;

    private final Map<KeycloakAuthorizationService.Roles, List<KeycloakAuthorizationService.Scopes>> permissions;

    public String getCode() {
        return code;
    }

    public static Resources of(String value) {
        return Arrays.stream(Resources.values())
                .filter(v -> value.equals(v.getCode()))
                .findFirst()
                .orElse(null);
    }

    Resources(String code, Map<KeycloakAuthorizationService.Roles, List<KeycloakAuthorizationService.Scopes>> permissions) {
        this.code = code;
        this.permissions = permissions;
    }


    public String getName() {
        return name();
    }

    public Map<KeycloakAuthorizationService.Roles, List<KeycloakAuthorizationService.Scopes>> getPermissions() {
        return permissions;
    }
}