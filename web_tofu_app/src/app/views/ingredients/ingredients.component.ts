import { Component } from '@angular/core';
import { IngredientResponse } from 'src/app/models/ingredient.interface';
import { IngredientService } from 'src/app/services/ingredient.service';

@Component({
  selector: 'app-ingredients',
  templateUrl: './ingredients.component.html',
  styleUrls: ['./ingredients.component.css'],
})
export class IngredientsComponent {
  ingredientList: IngredientResponse[] = [];
  indexIngredients = 0;
  selectedIngredient = {} as IngredientResponse;
  constructor(private ingredientService: IngredientService) {}
  ngOnInit(): void {
    this.getIngredients()
  }

  getIngredients(){
    this.ingredientService.getIngredientBySearch('', this.indexIngredients)
    .subscribe(res =>{
      if(res.totalPages>= this.indexIngredients){
        this.ingredientList = res.content
      }
    })
  }
  //TODO Crear listado de ingredientes (Ocupan toda la pantalla)
  //TODO Al seleccionar un ingrediente mostrar los detaññes
  //TODO Posibilidad de crear un ingrediente
  //TODO Posibilidad de borrar el ingrediente seleccionado
  //TODO Posibilidad de cambiar el ingrediente seleccionado
  //TODO Hacer la lista de ingredientes infinita

  getSelectedIngredient(ingredient: IngredientResponse){
    this.selectedIngredient = ingredient
  }
}
