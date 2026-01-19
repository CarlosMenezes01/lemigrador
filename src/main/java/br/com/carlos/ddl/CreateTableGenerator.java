package br.com.carlos.ddl;

import br.com.carlos.model.Coluna;
import br.com.carlos.model.Tabela;
import br.com.carlos.types.SqlTypeMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CreateTableGenerator {

    private final SqlTypeMapper typeMapper;

    public CreateTableGenerator(SqlTypeMapper typeMapper) {
        this.typeMapper = typeMapper;
    }

    public String gerarCreateTable(Tabela tabela) {

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ")
          .append(tabela.getNome())
          .append(" (\n");

        List<Coluna> colunas = tabela.getColunas();
        int index = 0;

        for (Coluna coluna : colunas) {

            sb.append("  ")
              .append(coluna.getNome())
              .append(" ")
              .append(typeMapper.mapearTipo(coluna));

            if (!coluna.isNullable()) {
                sb.append(" NOT NULL");
            }

            sb.append(",\n");
            index++;
        }

        // ===== CHAVE PRIMÁRIA =====
        List<Coluna> pkCols = colunas.stream()
                .filter(Coluna::isChavePrimaria)
                .collect(Collectors.toList());

        if (!pkCols.isEmpty()) {
            sb.append("  PRIMARY KEY (");
            for (int i = 0; i < pkCols.size(); i++) {
                sb.append(pkCols.get(i).getNome());
                if (i < pkCols.size() - 1) sb.append(", ");
            }
            sb.append(")\n");
        } else {
            // remove última vírgula
            sb.setLength(sb.length() - 2);
            sb.append("\n");
        }

        sb.append(");");

        return sb.toString();
    }
}



