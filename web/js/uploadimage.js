/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
const CLOUD_NAME = 'thinhtpt';
const UPLOAD_PRESET = 'yellow_moon_shop';
const SUB_FOLDER = 'yellow-moon-cakes';

function resetImage() {
    $("#fileInput").val("");
    $("#progress").hide();
}

function clearErrors() {
    $("#fileInput").removeClass("is-invalid");
    $("#fileInput").removeClass("is-valid");
    $("#fileErr").html("");
}

function deleteImage() {
    resetImage();
    $("#cake-image").val("");
    $("#previewImage").html("");
    $("#previewImageContainer").hide();
}

function uploadFile(file) {
    var uploadUrl = `https://api.cloudinary.com/v1_1/${CLOUD_NAME}/upload`;
    var xhr = new XMLHttpRequest();
    var formData = new FormData();
    xhr.open('POST', uploadUrl, true);
    xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');

    // Reset the upload progress bar
    $("#progress").show();
    $("#progress-bar").width(0);

    // Clear preview image
    $("#previewImage").html("");

    // Update progress bar
    xhr.upload.addEventListener("progress", function (e) {
        let percent = Math.round((e.loaded * 100.0) / e.total);
        $("#progress-bar").width(percent + "%");
    });

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // File uploaded successfully
            let response = JSON.parse(xhr.responseText);

            let imgUrl = response.secure_url;

            // Create a thumbnail of the uploaded image, with 150px width
            let tokens = imgUrl.split('/');
            tokens.splice(-3, 0, 'w_150,c_scale');

            let previewImg = new Image();
            previewImg.src = tokens.join('/');
            previewImg.alt = response.public_id;
            $("#previewImage").html(previewImg);
            $("#cake-image").val(imgUrl);
            resetImage();
            clearErrors();
        } else if (xhr.readyState === 4 && xhr.status === 0) {
            $("#previewImage").html("Please check your internet!");
        }
    };

    formData.append('upload_preset', UPLOAD_PRESET);
    formData.append('folder', SUB_FOLDER);
    formData.append('tags', 'lab_web'); // Optional tag
    formData.append('file', file);
    xhr.send(formData);
}   

$("#fileSelect").on("click", function (event) {
    event.preventDefault();
    if ($("#fileInput")) {
        $("#fileInput").click();
    }
});

$('#fileInput').bind('change', function () {
    if (this.files && this.files[0] && this.files[0].name.toLowerCase().match(/\.(jpg|jpeg|png)$/)) {
        if (this.files[0].size > 2 * 1024 * 1024) { // 2MB
            $("#fileInput").addClass("is-invalid");
            $("#fileErr").html("Size must be less than 2MB");
        } else {
            $("#fileInput").removeClass("is-invalid");
            $("#fileErr").html("");
            $("#previewImageContainer").show();
            uploadFile(this.files[0]);
        }
    } else {
        $("#fileInput").addClass("is-invalid");
        $("#fileErr").html("Your file is not an acceptable image");
    }
});

$("#deleteImage").on("click", function (event) {
    event.preventDefault();
    deleteImage();
});

$(document).ready(function () {
    if ($("#cake-image").val() === undefined || $("#cake-image").val() === '') {
        deleteImage();
    } else {
        resetImage();
    }
});