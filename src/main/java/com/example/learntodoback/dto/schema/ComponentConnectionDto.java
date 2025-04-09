package com.example.learntodoback.dto.schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComponentConnectionDto {
    private String uniqueElementId;
    private int elementId;
    private String sourceField;
    private String targetField;
    private String sourceOutputField;
    private String targetOutputField;
    private int targetElementId;
    private int targetOutputElementId;

    public ComponentConnectionDto(int elementId) {
        this.elementId = elementId;
    }
}

/*
{
    "uniqueElementId": "qwe",
    "elementId": "1",
    "sourceField": "plus",
    "targetField": "plus",
    "sourceOutputField": "minus",
    "targetOutputField": "neutral4",
    "targetElementId": "7",
    "targetOutputElementId": "6";
},
{
    "uniqueElementId": "asd",
    "elementId": "7",
    "sourceField": "plus",
    "targetField": "plus",
    "sourceOutputField": "minus",
    "targetOutputField": "neutral1",
    "targetElementId": "1",
    "targetOutputElementId": "6";
},
{
    "uniqueElementId": "zxc",
    "elementId": "6",
    "sourceField": "neutral4",
    "targetField": "minus",
    "sourceOutputField": "neutral1",
    "targetOutputField": "minus",
    "targetElementId": "1",
    "targetOutputElementId": "7";
}

[
  {
    "uniqueElementId": "qwe",
    "sourceElementId": 1,
    "targetElementId": 7,
    "sourceContactId": "plus",
    "targetContactId": "plus"
  },
  {
    "uniqueElementId": "asd",
    "sourceElementId": 7,
    "targetElementId": 6,
    "sourceContactId": "minus",
    "targetContactId": "neutral1"
  },
   {
    "uniqueElementId": "zxc",
    "sourceElementId": 6,
    "targetElementId": 1,
    "sourceContactId": "neutral4",
    "targetContactId": "minus"
  }
]


 */