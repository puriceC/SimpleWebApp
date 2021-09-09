getSuccessHandler = (btn) => {
    if (btn.value === 'Delete')
        return () => navigateTo('../..');
    if (btn.value === 'Create')
        return (retVal) => navigateTo('../' + retVal.id);
    if (btn.value === 'Rate')
        return () => location.reload();
    return (e) => alert('Success');
}

getType = (btn) => {
    if (btn.value === 'Delete')
        return 'DELETE';
    if (btn.value === 'Update' || btn.value === 'Rate')
        return 'PUT';
    return 'POST';
}

submitForm = (button) => {
    let form = $(button).parent();
    let type = getType(button);
    let formUrl = form.attr('action');
    let onSuccess = getSuccessHandler(button);
    let onError = (r,e)=>{console.log(r,e);};
    let formData = form.serializeArray().reduce(
                (data, field) => {
                    data[field.name] = field.value;
                    return data;
                },
                {});
    $.ajax({
      type: type,
      url: formUrl,
      data: JSON.stringify(formData),
      success: onSuccess,
      error: onError,
      dataType: "json",
      contentType : "application/json"
    });
}

navigateTo = (pathname) => {
    let url = new URL(location);

    if (url.pathname[url.pathname.length-1] !== '/')
        url.pathname += '/'
    url.pathname += pathname;

    location.assign(url);
}

addParams = (params) => {
    let url = new URL(location);

    let oldParams = url.search.replace('?', '').split('&');

    url.search = '?' + oldParams.reduce(
        (acc, el) => {
            if(!acc.includes(el.replace(/=.*$/, ''))) {
                acc += (acc.length !== 0) ? '&' : '';
                acc += el;
            }
            return acc;
        },
        params.join('&'))

    location.assign(url);
}