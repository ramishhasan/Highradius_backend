var pageno=1;
var url ="http://localhost:8080/Summer_Internship_Backend"

// $('form').submit(false)
const Rendertable = (data) =>{
    var row='';
    var className = 'c1'
    data.map(d=>{
        row+= 
        `
        <tr class="${className}">
            <td></td>
            <td><input type="checkbox" /></td>
            <td>${d.CustName}</td>
            <td>${d.CustNo}</td>
            <td>${d.InvoiceID}</td>
            <td>${d.InvoiceAmount}</td>
            <td>${d.DueDate}</td>
            <td>${d.PredPayDate}</td>
            <td>${d.Notes}</td>
            <td></td>
        </tr>` 
    })
    // console.log(row)
    $('.c2,.c1').remove()
    $('table').append(row)
    $('#edit-btn').prop('disabled', false)
    $('.modal-content.m2 > .modal-header > h2').text('Edit invoice')
}


$(document).ready(async function () {
    const resp = await fetch(url + "/fetch?page=" + pageno.toString());
    const body = await resp.json();
    Rendertable(body);

    $('#edit-btn').prop('disabled', true)
    $('.modal-content.m2 > .modal-header > h2').text('Edit invoice')

    $(document).on('click', 'input[type=checkbox]', function() {
        const count = $('input:checked').length;
        if (count > 1 || count == 0) {
            $('#edit-btn').prop('disabled', true)
            $('.modal-content.m2 > .modal-header > h2').text('Edit invoice')
        } else {
            const invoice = $('tr > td > input:checked').first()
                                        .closest('tr')
                                        .children()
                                        .eq(3).html()
            $('.modal-content.m2 > .modal-header > h2').text('Edit invoice #' + invoice)
            $('#edit-btn').prop('disabled', false)
        }
    });
});

const prev =async() =>{
    if(pageno<2) return;
    pageno-=1

    const resp = await fetch(url+"/fetch?page="+pageno.toString());
    const body = await resp.json()
    Rendertable(body)
} 

const next =async() =>{
    if(pageno>1000) return;
    pageno+=1

    const resp = await fetch(url+"/fetch?page="+pageno.toString());
    const body = await resp.json()
    // document.write(body)
    Rendertable(body)
} 

//For Add Functionality of data
var cancel = document.querySelectorAll(".cancel");
for (var i = 0; i < cancel.length; i++) {
    cancel[i].onclick = function () {
        for (var index in cancel) {
          cancel[index].style.display = "none";
        }
    };
}

async function add_details(){
    const form = $('.form_modal1_data');
    const inputs = form.find('input').not(':input[type=button], :input[type=submit], :input[type=reset]')
    const obj = {}

    inputs.map(function() {
        key = $(this).attr('name')
        value = $(this).val();
        obj[key] = value;
    });

    console.log(obj)

    await fetch(url+"/add", {    
        method: 'POST',
        body: new URLSearchParams(obj),
    });
    setTimeout(window.location.reload(),1000)
}

async function edit_details() {
    const obj = {};
    const tr = $('tr.c1 > td > input:checked').first().closest('tr');
    const form = $(".modal-content.m2");
    const inputs = form.find("textarea, input").not(":input[type=button], :input[type=submit], :input[type=reset]");

    obj.invoice = tr.children().eq(4).html();
    inputs.map(function () {
        key = $(this).attr("name");
        value = $(this).val();
        obj[key] = value;
    });

    await fetch(url + "/edit", {
        method: "POST",
        body: new URLSearchParams(obj),
        
    });    
}

function search() {
    const query = $('.searchbar > input').val();
    console.log(query)
    const trs = $('tr:not(.tabhead)');
    trs.map(function() {
        const tr = $(this)
        const td = tr.children().eq(4);
        const includes = td.html()
                           .includes(query);

        if (includes) {
            tr.show();
        } else {
            tr.hide();
        }
    });
}

