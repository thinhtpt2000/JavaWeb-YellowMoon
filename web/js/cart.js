/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global Swal */

$(document).ready(function () {
    $('.deleteItem').on('click', function (event) {
        event.preventDefault();
        let target = this.target;
        deleteItem(target);
    });

    $('input[name="txtAmount"]').on('blur', function (event) {
        let targetId = $(event.currentTarget).attr('target-id');
        let value = event.currentTarget.value;
        if (value <= 0) {
            let target = 'delete-form-' + targetId;
            deleteItem(target).then(result => {
                if (!result) {
                    location.reload();
                }
            });
        } else {
            let target = 'update-form-' + targetId;
            $(`#${target}`).submit();
        }
    });
});

async function deleteItem(formId) {
    let result = await Swal.fire({
        title: 'Are you sure?',
        text: "Your item will be deleted",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#6c757d',
        confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
        if (result.isConfirmed) {
            $(`#${formId}`).submit();
        }
        return result.isConfirmed;
    });
    return result;
}