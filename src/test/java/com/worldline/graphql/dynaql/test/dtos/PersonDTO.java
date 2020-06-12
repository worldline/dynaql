/*
 * Copyright 2020 jefrajames.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.worldline.graphql.dynaql.test.dtos;

import javax.json.bind.annotation.JsonbDateFormat;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author jefrajames
 */
public class PersonDTO {

    int id;
    String surname;
    String[] names;

    @JsonbDateFormat("dd/MM/yyyy") // This is for JSON-B
            LocalDate birthDate;

    List<AddressDTO> addresses;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "PersonDTO{" + "id=" + id + ", surname=" + surname + ", names=" + names + ", birthDate=" + birthDate + ", addresses=" + addresses + '}';
    }
    
    
    public static class AddressDTO {

        String code;
        String[] lines;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String[] getLines() {
            return lines;
        }

        public void setLines(String[] lines) {
            this.lines = lines;
        }

        @Override
        public String toString() {
            return "AddressDTO{" + "code=" + code + ", lines=" + lines + '}';
        }
        
    }

}
