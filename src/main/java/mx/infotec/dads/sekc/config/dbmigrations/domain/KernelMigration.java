
package mx.infotec.dads.sekc.config.dbmigrations.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "kernel"
})
public class KernelMigration {

    @JsonProperty("kernel")
    private Kernel kernel;

    @JsonProperty("kernel")
    public Kernel getKernel() {
        return kernel;
    }

    @JsonProperty("kernel")
    public void setKernel(Kernel kernel) {
        this.kernel = kernel;
    }

}
