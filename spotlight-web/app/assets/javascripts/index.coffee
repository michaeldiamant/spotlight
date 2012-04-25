$ ->
  $.get "/spy", (data) ->
    $.each data, (index, bar) ->
      $("#bars").append "<li>Date " + bar.date + "</li>"