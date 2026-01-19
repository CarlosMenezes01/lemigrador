package br.com.carlos.types;

import br.com.carlos.model.Coluna;

public class MariaDbTypeMapper implements SqlTypeMapper {

    @Override
    public String mapearTipo(Coluna coluna) {
        // MariaDB é altamente compatível com MySQL
        return coluna.getTipo();
    }
}
