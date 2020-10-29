/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


const MIN_PRICE = 0;
const MAX_PRICE = 5000000;
let slideData = {
    min: MIN_PRICE,
    max: MAX_PRICE,
    value: [MIN_PRICE, MAX_PRICE],
    focus: true,
    step: 1000
};

if ($("#min-price").val() === "" || $("#min-price").val() < MIN_PRICE) {
    $("#min-price").val(MIN_PRICE);
}
if ($("#max-price").val() === "" || $("#max-price").val() > MAX_PRICE) {
    $("#max-price").val(MAX_PRICE);
}

$("#price-slider").slider(slideData);
$("#price-slider").on("slide", function (event) {
    $("#min-price").val(event.value[0]);
    $("#max-price").val(event.value[1]);
});

$("#min-price").on('change', function () {
    updatePriceRange();
});

$("#max-price").on('change', function () {
    updatePriceRange();
});

function updatePriceRange() {
    let minPrice = parseInt($("#min-price").val());
    let maxPrice = parseInt($("#max-price").val());
    if (minPrice > maxPrice) {
        [minPrice, maxPrice] = [maxPrice, minPrice];
    }
    $("#price-slider").slider("setValue", [minPrice, maxPrice]);
    $("#min-price").val(minPrice);
    $("#max-price").val(maxPrice);
}
$(document).ready(function () {
    updatePriceRange();
});