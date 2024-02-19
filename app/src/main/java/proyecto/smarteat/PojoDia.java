package proyecto.smarteat;

public class PojoDia {
    int idPlan;
    int idDia;

    PojoTipoComida desayuno;
    PojoTipoComida almuerzo;
    PojoTipoComida comida;
    PojoTipoComida merienda;
    PojoTipoComida cena;

    public int getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(int idPlan) {
        this.idPlan = idPlan;
    }

    public int getIdDia() {
        return idDia;
    }

    public void setIdDia(int idDia) {
        this.idDia = idDia;
    }

    public PojoTipoComida getDesayuno() {
        return desayuno;
    }

    public void setDesayuno(PojoTipoComida desayuno) {
        this.desayuno = desayuno;
    }

    public PojoTipoComida getAlmuerzo() {
        return almuerzo;
    }

    public void setAlmuerzo(PojoTipoComida almuerzo) {
        this.almuerzo = almuerzo;
    }

    public PojoTipoComida getComida() {
        return comida;
    }

    public void setComida(PojoTipoComida comida) {
        this.comida = comida;
    }

    public PojoTipoComida getMerienda() {
        return merienda;
    }

    public void setMerienda(PojoTipoComida merienda) {
        this.merienda = merienda;
    }

    public PojoTipoComida getCena() {
        return cena;
    }

    public void setCena(PojoTipoComida cena) {
        this.cena = cena;
    }
}
